module nts.uk.pr.view.qmm002.a {
    __viewContext.ready(function() {
            var screenModel = new viewmodel.ScreenModel();
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
            });
    });
}