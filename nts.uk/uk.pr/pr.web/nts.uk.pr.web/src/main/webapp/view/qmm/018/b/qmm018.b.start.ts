module nts.uk.pr.view.qmm018.b {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);

            $("#swap-list-grid1_container").focus();
        });
    });
}
