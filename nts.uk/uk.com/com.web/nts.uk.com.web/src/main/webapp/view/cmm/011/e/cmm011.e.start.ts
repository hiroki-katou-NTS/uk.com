module nts.uk.com.view.cmm011.e {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#start-date").focus();
        });
    });
}