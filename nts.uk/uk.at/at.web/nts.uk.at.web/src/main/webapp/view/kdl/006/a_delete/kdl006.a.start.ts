module nts.uk.at.view.kdl006.a {
    __viewContext.ready(function() {
        var screenModel = new viewModel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#grid-list').focus();
        });
    });
}