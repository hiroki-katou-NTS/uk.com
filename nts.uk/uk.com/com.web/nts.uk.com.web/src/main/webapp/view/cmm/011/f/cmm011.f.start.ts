module nts.uk.com.view.cmm011.f {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#radio-group-box").focus();
        });
    });
}