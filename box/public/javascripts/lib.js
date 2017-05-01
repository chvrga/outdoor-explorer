
function PageViewModel(rootContainer){
	this.bound = false;
	this.view = ko.observable();

	this.loadView = function(url, model, container, success) {
		var pageModel = this;    	
		if(!pageModel.bound && $(rootContainer).length == 0)
		{
			ko.applyBindings(pageModel, $(rootContainer)[0]);
			pageModel.bound = true;
		} 

		$.get(url, function(htmlData) {
			pageModel.view("");
			pageModel.view(htmlData);

			if(model)
			{
				if ($(container).length) {
					try {
						ko.applyBindings(model, $(container)[0]);
					}catch(err){
						console.log("Hmmm..." + err);
					}
				}

				if (_.isFunction(model.render)) {
					model.render();                
				}

				if (_.isFunction(success)) {
					success(model);
				} else {
					console.log("success is not a function");
				}
			}
		}, "html");
	};            
};


ko.bindingHandlers.executeOnEnter = {
		init: function (element, valueAccessor, allBindings, viewModel) {
			var callback = valueAccessor();
			$(element).keypress(function (event) {
				var keyCode = (event.which ? event.which : event.keyCode);
				if (keyCode === 13) {
					callback.call(viewModel);
					return false;
				}
				return true;
			});
		}
};
