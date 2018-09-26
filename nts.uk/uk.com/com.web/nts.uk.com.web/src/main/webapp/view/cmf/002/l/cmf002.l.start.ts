module nts.uk.com.view.cmf002.l {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            _.delay(() => {
                __viewContext['screenModel'].timeDataFormatSetting().fixedValue() == 1 ? $('#L10_1_1').focus() : $('#L2_1').focus();
            }, 1000);
        });
    });
}