module nts.uk.at.view.ksu001.n {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        let screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('.ntsSearchBox.nts-editor.ntsSearchBox_Component').focus();
        });
    });
}
