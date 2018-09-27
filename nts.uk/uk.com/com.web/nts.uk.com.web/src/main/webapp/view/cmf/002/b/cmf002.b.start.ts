module nts.uk.com.view.cmf002.b {
    __viewContext.ready(function() {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("CMF002B_PARAMS", data, true);
        });
        let screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        });
    });
}
