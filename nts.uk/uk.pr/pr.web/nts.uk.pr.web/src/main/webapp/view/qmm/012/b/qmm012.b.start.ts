module nts.uk.pr.view.qmm012.b {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        
        screenModel.loadListData().done(function() {
            if(screenModel.statementItemDataList().length > 0) {
                screenModel.salaryItemId(screenModel.statementItemDataList()[0].salaryItemId);
            }

            __viewContext.bind(screenModel);
            
            if(screenModel.statementItemDataSelected().checkCreate()) {
                $("#B3_2").focus();
            } else {
                $("#B3_3").focus();
            }
        });
    });
}
