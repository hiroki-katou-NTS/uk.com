module nts.uk.pr.view.qmm016.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });
    });
}