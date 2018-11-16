module nts.uk.pr.view.qmm006.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            if (__viewContext['screenModel'].updateMode()) {
                $("#A3_3").focus();
            } else {
                $("#A3_2").focus();
            }
        });
    });
}
