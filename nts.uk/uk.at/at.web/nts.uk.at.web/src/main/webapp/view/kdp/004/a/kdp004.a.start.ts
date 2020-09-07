module nts.uk.at.view.kdp004.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.reCalGridWidthHeight();
        });
        $(window).resize(function () {
			screenModel.reCalGridWidthHeight();
		});
    });
}
