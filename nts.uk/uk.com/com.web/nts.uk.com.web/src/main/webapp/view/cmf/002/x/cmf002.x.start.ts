module nts.uk.com.view.cmf002.x {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => {
                $('#X3_1_1').find(".ntsStartDatePicker").focus();
            });
        });
    });
}