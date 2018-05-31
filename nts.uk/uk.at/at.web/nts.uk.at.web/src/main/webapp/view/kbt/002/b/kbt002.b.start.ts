module nts.uk.at.view.kbt002.b {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = new viewmodel.ScreenModel();
        __viewContext.viewModel.start().done(function() {
            __viewContext.bind(__viewContext.viewModel);
        });
    });
}