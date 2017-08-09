module nts.uk.pr.view.ksu006.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.eventClick();
        });
    });
}