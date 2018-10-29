module nts.uk.pr.view.qmm012.b {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        
        screenModel.loadListData().done(function() {
            if(screenModel.statementItemDataList().length > 0) {
                screenModel.currentKey(screenModel.statementItemDataList()[0].key);
            }

            __viewContext.bind(screenModel);

            $("#B3_3").focus();
        });
    });
}
