module nts.uk.com.view.cmf002.n {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            _.delay(() => {
                __viewContext['screenModel'].atWorkDataOutputItem().fixedValue() == 1 ? $('#N3_1').focus() : $('#N2_1_2').focus();
            }, 1000);
        });
    });
}
