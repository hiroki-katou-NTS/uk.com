module nts.uk.com.view.cdl027.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#C3_2").focus();
            screenModel.bindLinkClick();
        });
    });
}
