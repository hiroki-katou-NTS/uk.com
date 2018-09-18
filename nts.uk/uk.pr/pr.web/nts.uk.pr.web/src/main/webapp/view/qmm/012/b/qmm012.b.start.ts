module nts.uk.pr.view.qmm012.b {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        
        screenModel.loadListData().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
