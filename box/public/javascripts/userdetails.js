var rootContainer = "#mainContainer";

var userViewModel = null;
var parentViewModel	= null;

function UserDetailsViewModel(exploreViewModel) {
	userViewModel		= this;
	parentViewModel 	= exploreViewModel;

	var detailsUrl 		= "/userdetails";
	var container 		= "#userDetails"; 

	this.id 			= ko.observable("");
	this.username 		= ko.observable("");
	this.firstname 		= ko.observable("");
	this.lastname		= ko.observable("");
	this.avatar			= ko.observable("");

	this.likes		 	= ko.observableArray();
	this.wishes			= ko.observableArray();

	this.editMode		= ko.observable(false);

	function User(item) {
		this.username = item.username;
		this.avatar = item.avatar;
		this.firstname = item.firstname;
		this.lastname = item.lastname;
	};

	function Destination(item) {
		this.id = item.id;
		this.type = item.type;
		this.name = item.name;
		this.country = item.country;
		this.description = item.description;
		this.website = item.website;
		this.logo = item.logo;
		this.background = item.background;

		if (this.type == "SKI") {
			this.altitude = item.likes_altitude;
			this.slopeLength = item.likes_slopeLength;
			this.slopeCounts = item.likes_slopeCounts;
		}
	};

	this.loadView = function(item) {
		var viewModel = this;
		var successBackDetails = function() {
			userViewModel.id(item["id"]);
			userViewModel.username(item["username"]);
			userViewModel.avatar(item["avatar"]);
			userViewModel.firstname(item["firstname"]);
			userViewModel.lastname(item["lastname"]);

			viewModel.ajax_findWhatUserLikes();
			viewModel.ajax_findWhatUserWishes();
		}

		parentViewModel.loadView(detailsUrl, userViewModel, container, successBackDetails);
	};

	this.backHome = function() {
		parentViewModel.showUsers();
	}

	this.editUser = function() {
		this.editMode(true);
	};

	this.cancelEdit = function() {
		this.editMode(false);
	};

	this.showDestinationDetails = function(item) {
		parentViewModel.showDestinationDetails(item);
	};

	this.ajax_findWhatUserLikes = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/findWhatUserLikes',
				type : 'get',
				dataType : 'json',
				data : {
					userid : viewModel.id
				},
				success : successFindLikes,
				error : errorFindLikes
			})
		} catch (err) {
			console.log("ajax_findWhatUserLikes - exception");
		}

		// Callbacks for http
		function successFindLikes(items) {
			viewModel.likes.removeAll();
			$.map(items, function(item) {
				viewModel.likes.push(new Destination(item));
			});
		}
		function errorFindLikes(error) {
			console.log("errorFindLikes.error: " + JSON.stringify(error));
		}
	};

	this.ajax_findWhatUserWishes = function() {
		var viewModel = this;
		try {
			$.ajax({
				url : '/findWhatUserWishes',
				type : 'get',
				dataType : 'json',
				data : {
					userid : viewModel.id
				},
				success : successFindWishes,
				error : errorFindWishes
			})
		} catch (err) {
			console.log("ajax_findWhatUserWishes - exception");
		}

		// Callbacks for http
		function successFindWishes(items) {
			viewModel.wishes.removeAll();
			$.map(items, function(item) {
				viewModel.wishes.push(new Destination(item));
			});
		}
		function errorFindWishes(error) {
			console.log("errorFindWishes.error: " + JSON.stringify(error));
		}
	};
};

function editResult() {
	var framecontent = $("#edit_target").contents().find("body").html();
	if (framecontent.match("^success")) {
		parentViewModel.showUsers();
	} else if (framecontent.match("^error")) {
		userViewModel.editMode(false);
		$("#edit_error").removeClass("hidden");
		$("#edit_target").html(framecontent.substring(2,framecontent.length));
	}

};	