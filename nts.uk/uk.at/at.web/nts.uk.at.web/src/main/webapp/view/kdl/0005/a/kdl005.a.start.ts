module nts.uk.at.view.kdl0005.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl0005.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}