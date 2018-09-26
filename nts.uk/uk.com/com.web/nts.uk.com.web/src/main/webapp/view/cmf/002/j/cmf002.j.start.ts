module nts.uk.com.view.cmf002.j {
     __viewContext.ready(function() {
    __viewContext['screenModel'] = new viewmodel.ScreenModel();
            __viewContext['screenModel'].start().done(function() {
                __viewContext.bind(__viewContext['screenModel']);
                _.delay(() => {
                __viewContext['screenModel'].characterDataFormatSetting().fixedValue() == 1 ? $('#J7_1').focus() : $('#J2_1').focus(); 
            }, 1000);
            });
    });
}