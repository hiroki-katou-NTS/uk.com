module nts.uk.pr.view.qmm039.b {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            $("#B1_3").focus();
        });
    });
}