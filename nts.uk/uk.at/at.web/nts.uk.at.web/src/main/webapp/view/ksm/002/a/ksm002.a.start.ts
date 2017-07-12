__viewContext.ready(function() {
    var viewModel = {
        viewModelA: new ksm002.a.viewmodel.ScreenModel(),
        viewModelB : new ksm002.b.viewmodel.ScreenModel()
    };
    viewModel.start().done(() => {
    __viewContext.bind(viewModel);
    });
});