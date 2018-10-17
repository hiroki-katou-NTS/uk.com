module nts.uk.pr.view.qmm002.a {
    __viewContext.ready(function() {
            var screenModel = new viewmodel.ScreenModel();
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
                if (screenModel.listBank().length == 0) {
                    screenModel.openDialogQmm002d();
                }
            });
    });
}