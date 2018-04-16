module nts.uk.com.view.cmf001.i {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {
            if (screenModel.inputMode) {
                $("#I2_2").focus();
            } else {
                $("#I5_2").focus();
            }
        });
    });
}