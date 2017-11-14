module nts.uk.at.view.kdl006.b {
    __viewContext.ready(function() {
        var screenModel = new viewModel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}