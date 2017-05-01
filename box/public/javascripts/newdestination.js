var rootContainer = "#mainContainer";

var newdestinationViewModel = null;
var parentViewModel	= null;

function NewDestinationViewModel(exploreViewModel) {
	newdestinationViewModel	= this;
	parentViewModel 		= exploreViewModel;

	var detailsUrl 			= "/newdestination";
	var container 			= "#newdestinationContainer"; 

	this.selectedTypeValue	= ko.observable("city");

	this.loadView = function() {
		var successBackCreate = function() {
		}
		parentViewModel.loadView(detailsUrl, newdestinationViewModel, container, successBackCreate);
	};

	this.backHome = function() {
		parentViewModel.init();
	};
};

function newdestinationResult() {
	var framecontent = $("#form_target").contents().find("body").html();
	if (framecontent.match("^success")) {
		location.href = "/";
	} else if (framecontent.match("^error")) {
		$("#newdestination_error").removeClass("hidden");
	} else {
		$("#createDestError").html(framecontent.substring(4,framecontent.length-5));
		$("#newdestination_error").removeClass("hidden");
	}
}	

