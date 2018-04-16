module nts.uk.com.view.cmf002.n {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.com.view.cmf002.n.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            $('#buttonImport').focus();
    });
}
