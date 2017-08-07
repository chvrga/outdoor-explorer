var rootContainer = "#mainContainer";
var headContainer = "#headContainer";

MainViewModel.prototype = new PageViewModel(rootContainer);

var mainViewModel = new MainViewModel();
mainViewModel.init();

function MainViewModel() {
	var mainViewModelUrl = "/explorer";
	var container = "#explorerMain";

	this.resorts = ko.observableArray();
	this.suggestions = ko.observableArray();

	this.searchtext = ko.observable("");
	this.isAdvanced = ko.observable(false);
	this.selectedTypeValue	= ko.observable("all");
	this.selectedFieldValue	= ko.observable("all");


	function Destination(item) {
		this.id = item.id;
		this.type = item.type;
		this.name = item.name;
		this.country = item.country;
		this.description = item.description;
		this.website = item.website;
		this.logo = item.logo;
		this.background = item.background;

		this.altitude = item.altitude;
		this.slopeLength = item.slopeLength;
		this.slopeCounts = item.slopeCounts;
	}
	;

	this.addNewDestination = function() {
		NewDestinationViewModel.prototype = new PageViewModel(rootContainer);
		var newDestination = new NewDestinationViewModel(mainViewModel);
		newDestination.loadView();
	};

	this.showDestinationDetails = function(input) {
		DestinationDetailsViewModel.prototype = new PageViewModel(rootContainer);
		var resortDetails = new DestinationDetailsViewModel(mainViewModel);
		resortDetails.loadView(input);
	};

	this.showUserDetails = function(input) {
		UserDetailsViewModel.prototype = new PageViewModel(rootContainer);
		var userDetails = new UserDetailsViewModel(mainViewModel);
		userDetails.loadView(input);
	};

	this.searchMe = function() {
		mainViewModel.ajax_getDestinations(mainViewModel.searchtext(), mainViewModel.selectedTypeValue(), mainViewModel.selectedFieldValue());
	};

	this.showAdvanced = function() {
		mainViewModel.isAdvanced(!mainViewModel.isAdvanced());
	};

	this.quickSearch = function() {
		if (mainViewModel.searchtext().length > 2) {
			mainViewModel.ajax_getQuickSearch(mainViewModel.searchtext(), mainViewModel.selectedTypeValue(), mainViewModel.selectedFieldValue());
		} else {
			mainViewModel.suggestions.removeAll();
		}
	};

	this.searchAll	= function() {
		mainViewModel.suggestions.removeAll();
		mainViewModel.searchtext("");
		mainViewModel.selectedTypeValue("all");
		mainViewModel.selectedFieldValue("all");
		mainViewModel.ajax_getDestinations(mainViewModel.searchtext(), mainViewModel.selectedTypeValue(), mainViewModel.selectedFieldValue());
	};

	this.register = function() {
		RegisterViewModel.prototype = new PageViewModel(rootContainer);
		var registerUser = new RegisterViewModel(mainViewModel);
		registerUser.loadView();

	};

	this.showUsers = function() {
		UsersViewModel.prototype = new PageViewModel(rootContainer);
		var users = new UsersViewModel(mainViewModel);
		users.loadView();

	};

	this.ajax_getDestinations = function(searchFor, typeFilter, fieldFilter) {
		var veiewModel = this;
		try {
			$.ajax({
				url : '/searchDestinations',
				type : 'get',
				dataType : 'json',
				data : {
					input : searchFor,
					type : typeFilter,
					field : fieldFilter
				},
				success : successSearchDestinations,
				error : errorSearchDestinations
			})
		} catch (err) {
			console.log("ajax_getDestinations - exception");
		}

		// Callbacks for http
		function successSearchDestinations(items) {
			mainViewModel.resorts.removeAll();
			$.map(items, function(item) {
				mainViewModel.resorts.push(new Destination(item));
			});
			mainViewModel.suggestions.removeAll();
			mainViewModel.searchtext("");

		}
		function errorSearchDestinations(error) {
			console.log("errorSearchDestinations.error: " + JSON.stringify(error));
		}
	};

	this.ajax_getQuickSearch = function(searchFor, typeFilter, fieldFilter) {
		var veiewModel = this;
		try {
			$.ajax({
				url : '/searchDestinations',
				type : 'get',
				dataType : 'json',
				data : {
					input : searchFor,
					type : typeFilter,
					field : fieldFilter
				},
				success : successQuickSearch,
				error : errorQucikSearch
			})
		} catch (err) {
			console.log("ajax_getQuickSearch - exception");
		}

		// Callbacks for http
		function successQuickSearch(items) {
			mainViewModel.suggestions.removeAll();
			$.map(items, function(item) {
				mainViewModel.suggestions.push(new Destination(item));
			});
		}
		function errorQucikSearch(error) {
			console.log("errorQucikSearch.error: " + JSON.stringify(error));
		}
	};

	this.ajax_getAllDestinations = function() {
		var veiewModel = this;
		try {
			$.ajax({
				url : '/getAllDestinations',
				type : 'get',
				dataType : 'json',
				success : successAllDestinations,
				error : errorAllDestinations
			})
		} catch (err) {
			console.log("ajax_getAllDestinations - exception");
		}

		// Callbacks for http
		function successAllDestinations(items) {
			mainViewModel.resorts.removeAll();
			$.map(items, function(item) {
				mainViewModel.resorts.push(new Destination(item));
			});
			mainViewModel.suggestions.removeAll();
			mainViewModel.searchtext("");

		}
		function errorAllDestinations(error) {
			console.log("errorSearchDestinations.error: " + JSON.stringify(error));
		}
	};

	this.init = function() {
		var viewModel = this;

		var successBack = function() {
			try {
				ko.applyBindings(mainViewModel, $(container)[0]);
				ko.applyBindings(mainViewModel, $(headContainer)[0]);
			} catch (err) {
				alert("explorer - errors in container JS: " + err);
			}

			mainViewModel.suggestions.removeAll();

			if (mainViewModel.searchtext() != "" || viewModel.selectedFieldValue() != "all" || viewModel.selectedTypeValue() != "all")
				mainViewModel.ajax_getDestinations(mainViewModel.searchtext(), mainViewModel.selectedTypeValue(), mainViewModel.selectedFieldValue());
			else 
				viewModel.ajax_getAllDestinations();
		}

		viewModel.loadView(mainViewModelUrl, viewModel, rootContainer,
				successBack);
	};

};
