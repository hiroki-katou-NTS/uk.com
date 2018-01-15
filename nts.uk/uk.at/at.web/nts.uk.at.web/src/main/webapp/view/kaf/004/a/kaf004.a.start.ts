module nts.uk.at.view.kaf004.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kaf004.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}