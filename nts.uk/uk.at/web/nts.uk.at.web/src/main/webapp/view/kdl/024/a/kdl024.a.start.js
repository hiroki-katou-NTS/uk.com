__viewContext.ready(function () {
    var viewModel = new kdl024.a.viewmodel.ScreenModel();
    viewModel.start().done(function () {
        __viewContext.bind(viewModel);
        $('#inpCode').focus();
    });
});
//# sourceMappingURL=kdl024.a.start.js.map