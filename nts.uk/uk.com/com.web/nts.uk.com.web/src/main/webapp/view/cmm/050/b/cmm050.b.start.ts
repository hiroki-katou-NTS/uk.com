module nts.uk.com.view.cmm050.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start_page().done(function() {
            __viewContext.bind(screenModel);
            $("#email1").focus();
        });
    });
}
