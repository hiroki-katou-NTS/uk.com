module nts.uk.com.view.cmf001.k {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            $('#K3_1_container').focus();
        });
    });
}