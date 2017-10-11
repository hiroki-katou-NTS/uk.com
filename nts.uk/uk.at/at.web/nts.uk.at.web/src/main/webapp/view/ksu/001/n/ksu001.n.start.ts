module nts.uk.com.view.ksu001.n {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
