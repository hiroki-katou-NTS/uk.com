module nts.uk.pr.view.qmm017.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel()
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}