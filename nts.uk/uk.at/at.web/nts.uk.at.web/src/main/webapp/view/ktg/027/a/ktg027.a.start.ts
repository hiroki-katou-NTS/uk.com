module nts.uk.at.view.ktg027.a {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ktg027.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}