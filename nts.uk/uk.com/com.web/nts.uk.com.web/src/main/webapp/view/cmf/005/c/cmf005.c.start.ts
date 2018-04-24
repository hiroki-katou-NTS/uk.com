module nts.uk.com.view.cmf005.c {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
//        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
//        });
    });
}