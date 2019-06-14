module nts.uk.pr.view.qmm006.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            if (__viewContext['screenModel'].listSourceBank().length > 0) {
                __viewContext['screenModel'].setSelectedCode(__viewContext['screenModel'].listSourceBank()[0].code);
            } else {
                __viewContext['screenModel'].setSelectedCode("");
            }
        });
    });
}
