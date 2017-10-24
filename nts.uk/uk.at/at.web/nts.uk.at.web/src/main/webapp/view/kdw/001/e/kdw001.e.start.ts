module nts.uk.pr.view.kdw001.e {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdw001.e.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}