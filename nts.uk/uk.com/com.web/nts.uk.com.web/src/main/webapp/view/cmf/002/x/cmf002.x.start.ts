module nts.uk.com.view.cmf002.x {
    __viewContext.ready(function() {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("CMF002X_PARAMS", data);
        });
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => {
                $('#X3_1_1').find(".ntsStartDatePicker").focus();
            });
        });
    });
}