module nts.uk.at.view.kmk003.sample {
    __viewContext.ready(function() {
        var screenModel = new viewModel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}