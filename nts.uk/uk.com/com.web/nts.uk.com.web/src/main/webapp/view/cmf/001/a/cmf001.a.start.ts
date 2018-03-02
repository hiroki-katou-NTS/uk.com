module nts.uk.com.view.cmf001.a {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.com.view.cmf001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#buttonImport').focus();
        });
    });
}
