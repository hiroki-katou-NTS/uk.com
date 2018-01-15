module nts.uk.at.view.ksm004.e {
    __viewContext.ready(function() {
        var screenModel = new ksm004.e.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.markDuplicate();
        });
    });
}