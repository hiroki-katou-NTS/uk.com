module nts.uk.pr.view.qmm002.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].startPage().done(function() {
        __viewContext.bind(__viewContext['screenModel']);
            if (__viewContext['screenModel'].listBank().length == 0) {
                __viewContext['screenModel'].openDialogQmm002d();
            } else {
                if (__viewContext['screenModel'].listBranch().length > 0)
                    __viewContext['screenModel'].setSelectedCode(__viewContext['screenModel'].listBranch()[0].id);
                else {
                    __viewContext['screenModel'].setSelectedCode(__viewContext['screenModel'].listBank()[0].code());
                }
            }
        });
    });
}