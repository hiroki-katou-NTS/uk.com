module nts.uk.com.view.cmf002.k {
    import model = cmf002.share.model;
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
             _.delay(() => {
                 __viewContext['screenModel'].dateDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE ? $('#K2_1').focus() : $('#K4_1').focus();
            }, 1000);
        });
    });
}