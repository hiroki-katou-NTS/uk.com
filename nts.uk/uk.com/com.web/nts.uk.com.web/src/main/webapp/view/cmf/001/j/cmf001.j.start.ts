module nts.uk.com.view.cmf001.j {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {
            if (screenModel.inputMode) {
                $("#J2_2").focus();
            } else {
                $("#J8_2").focus();
            }
        });
    });
}