module nts.uk.com.view.cmf002.b {
    __viewContext.ready(function() {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("CMF002B_PARAMS", data, true);
        });
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
        });

    });
}
