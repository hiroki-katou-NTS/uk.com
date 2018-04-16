module nts.uk.com.view.cmf001.l {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {
            if (screenModel.inputMode) {
                $("#combo-box").find("input").first().focus();
            } else {
                $("#L3_2").focus();
            }
        });
    });
}