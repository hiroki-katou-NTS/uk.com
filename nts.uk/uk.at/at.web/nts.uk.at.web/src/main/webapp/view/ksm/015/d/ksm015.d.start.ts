module nts.uk.at.view.ksm015.d {
	__viewContext.ready(function() {
		var screenModel = new viewmodel.ScreenModel();
		screenModel.startPage().done(function() {
			__viewContext.bind(screenModel);
			screenModel.reCalGridWidth();
        });
		$(window).resize(function () {
			screenModel.reCalGridWidth();
		});
		
	});
}