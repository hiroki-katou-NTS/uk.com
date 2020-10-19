module nts.uk.cloud.view.cld001.a {
	__viewContext = window['__viewContext'] || {};
	__viewContext.ready(function () {
	    var screenModel = new viewmodel.ScreenModel();
	    screenModel.startPage().done(function() {
			__viewContext.bind(screenModel);
        });
	});
}