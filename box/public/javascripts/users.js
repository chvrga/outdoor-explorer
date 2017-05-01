var rootContainer = "#mainContainer";

var usersViewModel = null;
var parentViewModel = null;

function UsersViewModel(exploreViewModel) {
	usersViewModel = this;
	parentViewModel = exploreViewModel;

	var usersUrl = "/users";
	var container = "#usersMain";

	this.users = ko.observableArray();
	this.searchtext = ko.observable("");

	function User(item) {
		this.id = item.id;
		this.username = item.username;
		this.firstname = item.firstname;
		this.lastname = item.lastname;
		this.avatar = item.avatar;
	};

	this.showUserDetails = function(input) {
		parentViewModel.showUserDetails(new User(input));
	};

	this.ajax_getUsers = function(searchFor) {
		var veiewModel = this;
		try {
			$.ajax({
				url : '/getAllUserData',
				type : 'get',
				dataType : 'json',
				data : {
					input : searchFor
				},
				success : successSearchUsers,
				error : errorSearchUsers
			})
		} catch (err) {
			console.log("ajax_getUsers - exception");
		}

		// Callbacks for http
		function successSearchUsers(items) {
			usersViewModel.users.removeAll();
			$.map(items, function(item) {
				usersViewModel.users.push(new User(item));
			});
		}
		function errorSearchUsers(error) {
			console.log("errorSearchUsers.error: " + JSON.stringify(error));
		}
	};

	this.loadView = function() {
		var viewModel = this;

		var successBackUsers = function() {
			viewModel.ajax_getUsers("");
		}

		parentViewModel.loadView(usersUrl, usersViewModel, container,
				successBackUsers);
	};

}
