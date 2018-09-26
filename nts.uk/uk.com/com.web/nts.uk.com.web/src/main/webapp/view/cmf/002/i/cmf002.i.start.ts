module nts.uk.com.view.cmf002.i {
    import model = cmf002.share.model;
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            _.delay(() => {
                __viewContext['screenModel'].numberDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE ? $('#I2_1').focus() : $('#I7_1').focus();
            }, 1000);
        });
    });
}