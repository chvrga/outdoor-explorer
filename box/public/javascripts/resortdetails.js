var rootContainer = "#mainContainer";

var detailsViewModel = null;
var parentViewModel	= null;

function DestinationDetailsViewModel(exploreViewModel) {
	detailsViewModel	= this;
	parentViewModel 	= exploreViewModel;

	var detailsUrl 		= "/resortdetails";
	var container 		= "#resortDetails"; 

	this.meter 			= ko.observable(" m");
	this.kilometer		= ko.observable(" km");

	this.id 			= ko.observable("");
	this.type 			= ko.observable("");
	this.name 			= ko.observable("");
	this.country 		= ko.observable("");
	this.description	= ko.observable("");
	this.website		= ko.observable("");
	this.logo 			= ko.observable("");
	this.background		= ko.observable("");
	this.altitude 		= ko.observable("");
	this.slopeLength 	= ko.observable("");
	this.slopeCounts 	= ko.observableArray();
	this.canvasId		= ko.observable("");

	this.usersLiked	 	= ko.observableArray();
	this.usersSaved	 	= ko.observableArray();

	this.similarByLike	= ko.observableArray();
	this.similarBySave	= ko.observableArray();

	this.IlikeIt		= ko.observable(false);
	this.savedIt		= ko.observable(false);


	this.editMode		= ko.observable(false);

	function User(item) {
		this.id	= item.id;		
		this.username = item.username;
		this.avatar = item.avatar;
		this.firstname = item.firstname;
		this.lastname = item.lastname;
	};

	function SimilarDestination(item) {
		this.similar_id = item.id;
		this.similar_type = item.type;
		this.similar_name = item.name;
		this.similar_country = item.country;
		this.similar_description = item.description;
		this.similar_website = item.website;
		this.similar_logo = item.logo;
		this.similar_background = item.background;

		if (this.similar_type == "SKI") {
			this.similar_altitude = item.altitude;
			this.similar_slopeLength = item.slopeLength;
			this.similar_slopeCounts = item.slopeCounts;
		}
	};

	function Destination(item) {
		this.id = item.similar_id;
		this.type = item.similar_type;
		this.name = item.similar_name;
		this.country = item.similar_country;
		this.description = item.similar_description;
		this.website = item.similar_website;
		this.logo = item.similar_logo;
		this.background = item.similar_background;

		if (this.type == "SKI") {
			this.altitude = item.similar_altitude;
			this.slopeLength = item.similar_slopeLength;
			this.slopeCounts = item.similar_slopeCounts;
		}
	};

	this.loadView = function(item) {
		var viewModel = this;
		var successBackDetails = function() {
			detailsViewModel.id(item["id"]);
			detailsViewModel.type(item["type"]);
			detailsViewModel.name(item["name"]);
			detailsViewModel.country(item["country"]);
			detailsViewModel.description(item["description"]);
			detailsViewModel.website(item["website"]);
			detailsViewModel.logo(item["logo"]);
			detailsViewModel.background(item["background"]);

			if (detailsViewModel.type() == "SKI") {
				detailsViewModel.altitude(item["altitude"]);
				detailsViewModel.slopeLength(item["slopeLength"]);
				detailsViewModel.slopeCounts(item["slopeCounts"]);
				detailsViewModel.canvasId("skiChart" + item["id"]);
				makeSlopesChart();
			}

			viewModel.ajax_doILikeIt();
			viewModel.ajax_findWhoLikesDestination();
			viewModel.ajax_findSimilarByLikes();
			viewModel.ajax_findWhoSavedDestination();
			viewModel.ajax_findSimilarByWishlist();
		}

		parentViewModel.loadView(detailsUrl, detailsViewModel, container, successBackDetails);
	};

	this.backHome = function() {
		parentViewModel.init();
	}

	this.editDestination = function() {
		this.editMode(true);
	};

	this.cancelEdit = function() {
		this.editMode(false);
	};

	this.loveIt = function() {
		this.ajax_addToList("likes", !detailsViewModel.IlikeIt());
	};

	this.addToWishlist = function() {
		this.ajax_addToList("wishlist", !detailsViewModel.savedIt());
	};

	this.showUserDetails = function(user) {
		parentViewModel.showUserDetails(new User(user));
	};

	this.showDestinationDetails = function(item) {
		parentViewModel.showDestinationDetails(new Destination(item));
	}

	var makeSlopesChart = function() {
		var id = detailsViewModel.id();
		var difficult = detailsViewModel.slopeCounts()["difficult"];
		var medium = detailsViewModel.slopeCounts()["medium"];
		var easy = detailsViewModel.slopeCounts()["easy"];
		var freeride = detailsViewModel.slopeCounts()["freeride"];

		var ctx = document.getElementById("skiChart" + id);
		if (ctx != null)
			var skiChart = new Chart(ctx, {
				type : 'doughnut',
				responsive : false,
				data : {
					labels : [ "Difficult", "Medium", "Easy", "Freeride" ],
					datasets : [ {
						data : [ difficult, medium, easy, freeride ],
						backgroundColor : [ 'rgba(10, 10, 10, 0.2)',
						                    'rgba(255, 99, 132, 0.2)',
						                    'rgba(54, 162, 235, 0.2)',
						                    'rgba(75, 192, 192, 0.2)' ],
						                    hoverBackgroundColor : [ 'rgba(100, 100, 100, 1)',
						                                             'rgba(255,99,132,1)', 'rgba(54, 162, 235, 1)',
						                                             'rgba(75, 192, 192, 1)' ],
					} ]
				}, 
				options: {
					legend: {
						display: true,
						position: 'right',
						labels: {
							boxWidth: 20,
							fontSize: 10
						}
					}
				}
			});
	};

	this.ajax_addToList = function(type, status) {
		var viewModel = this;
		try {
			$.ajax({
				url : '/addToList',
				type : 'post',
				dataType : 'json',
				data : {
					destination : viewModel.id,
					listtype	: type,
					add			: status
				},
				success : successAddToList,
				error : errorAddToList
			})
		} catch (err) {
			console.log("ajax_addToList - exception");
		}

		function successAddToList(items) {
			viewModel.ajax_doILikeIt();
			viewModel.ajax_findWhoLikesDestination();
			viewModel.ajax_findSimilarByLikes();
			viewModel.ajax_findWhoSavedDestination();
			viewModel.ajax_findSimilarByWishlist();
		}
		function errorAddToList(error) {
			console.log("errorAddToList.error: " + JSON.stringify(error));
		}
	};

	this.ajax_findWhoLikesDestination = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/findWhoLikesDestination',
				type : 'get',
				dataType : 'json',
				data : {
					destination : viewModel.id
				},
				success : successFindLikers,
				error : errorFindLikers
			})
		} catch (err) {
			console.log("ajax_findWhoLikesDestination - exception");
		}

		function successFindLikers(items) {
			viewModel.usersLiked.removeAll();
			$.map(items, function(item) {
				viewModel.usersLiked.push(new User(item));
			});
		}
		function errorFindLikers(error) {
			console.log("errorFindLikers.error: " + JSON.stringify(error));
		}
	};

	this.ajax_findWhoSavedDestination = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/findWhoSavedDestination',
				type : 'get',
				dataType : 'json',
				data : {
					destination : viewModel.id
				},
				success : successFindSavers,
				error : errorFindSavers
			})
		} catch (err) {
			console.log("ajax_findWhoLikesDestination - exception");
		}

		function successFindSavers(items) {
			viewModel.usersSaved.removeAll();
			$.map(items, function(item) {
				viewModel.usersSaved.push(new User(item));
			});
		}
		function errorFindSavers(error) {
			console.log("errorFindSavers.error: " + JSON.stringify(error));
		}
	};

	this.ajax_findSimilarByLikes = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/findSimilarByLikes',
				type : 'get',
				dataType : 'json',
				data : {
					destination : viewModel.id
				},
				success : successFindSimilarByLikes,
				error : errorFindSimilarByLikes
			})
		} catch (err) {
			console.log("ajax_findSimilarByLikes - exception");
		}

		function successFindSimilarByLikes(items) {
			viewModel.similarByLike.removeAll();

			$.map(items, function(item) {
				viewModel.similarByLike.push(new SimilarDestination(item));
			});
		}
		function errorFindSimilarByLikes(error) {
			console.log("errorFindSimilarByLikes.error: " + JSON.stringify(error));
		}
	};

	this.ajax_findSimilarByWishlist = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/findSimilarByWishlist',
				type : 'get',
				dataType : 'json',
				data : {
					destination : viewModel.id
				},
				success : successFindSimilarByWishlist,
				error : errorFindSimilarByWishlist
			})
		} catch (err) {
			console.log("ajax_findSimilarByWishlist - exception");
		}

		function successFindSimilarByWishlist(items) {
			viewModel.similarBySave.removeAll();
			$.map(items, function(item) {
				viewModel.similarBySave.push(new SimilarDestination(item));
			});
		}
		function errorFindSimilarByWishlist(error) {
			console.log("errorFindSimilarByWishlist.error: " + JSON.stringify(error));
		}
	};

	this.ajax_doILikeIt = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/doILikeIt',
				type : 'get',
				dataType : 'json',
				data : {
					destinationId : viewModel.id
				},
				success : successDoILikeIt,
				error : errorDoILikeIt
			})
		} catch (err) {
			console.log("ajax_doILikeIt - exception");
		}

		function successDoILikeIt(items) {
			if (JSON.stringify(items["liked"]) == "true") {
				viewModel.IlikeIt(true);
			} else {
				viewModel.IlikeIt(false);
			}
			if (JSON.stringify(items["saved"]) == "true") {
				viewModel.savedIt(true);
			} else {
				viewModel.savedIt(false);
			} 
		}
		function errorDoILikeIt(error) {
			console.log("errorDoILikeIt.error: " + JSON.stringify(error));
		}
	};
};

function editDstResult() {
	var framecontent = $("#edit_target").contents().find("body").html();
	if (framecontent.match("^success")) {
		parentViewModel.loadView();
	} else if (framecontent.match("^error")) {
		detailsViewModel.editMode(false);
		$("#edit_error").removeClass("hidden");
		$("#edit_target").html(framecontent.substring(2,framecontent.length));
	}
};	
