module nts.uk.at.view.kmk015.c {
    __viewContext.ready(function() {
        var parentCodes = nts.uk.ui.windows.getShared('parentCodes');
        var screenModel = new viewmodel.ScreenModel(parentCodes);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#inp-period-startYMD").focus();
        });
    });
}