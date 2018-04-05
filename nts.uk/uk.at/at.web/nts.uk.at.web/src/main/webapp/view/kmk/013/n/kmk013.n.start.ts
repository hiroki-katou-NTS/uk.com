module nts.uk.at.view.kmk013.n {
    __viewContext.ready(function() {
        var screenModel = new n.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
