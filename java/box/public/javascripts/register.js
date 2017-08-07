var rootContainer = "#mainContainer";

var registerViewModel = null;
var parentViewModel	= null;

function RegisterViewModel(exploreViewModel) {
	registerViewModel	= this;
	parentViewModel 	= exploreViewModel;

	var detailsUrl 		= "/register";
	var container 		= "#registerContainer"; 

	this.loadView = function() {
		var successBackRegister = function() {
		}
		parentViewModel.loadView(detailsUrl, registerViewModel, container, successBackRegister);
	};

	this.backHome = function() {
		parentViewModel.init();
	};
};

function registerResult() {
	var framecontent = $("#form_target").contents().find("body").html();
	if (framecontent.match("^success")) {
		location.href = "/login";
	} else if (framecontent.match("^error")) {
		$("#register_error").removeClass("hidden");
		$("#form_target").html(framecontent.substring(2,framecontent.length));
	}
}	

