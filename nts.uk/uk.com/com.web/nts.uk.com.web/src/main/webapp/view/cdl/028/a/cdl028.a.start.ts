module nts.uk.com.view.cdl028.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#A1_2").focus();
        });
    });
}