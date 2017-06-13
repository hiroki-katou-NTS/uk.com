__viewContext.ready(function() {
    var viewModel = new kdl014.b.viewmodel.ScreenModel();
    viewModel.start().done(() => {
        __viewContext.bind(viewModel);
    });
})