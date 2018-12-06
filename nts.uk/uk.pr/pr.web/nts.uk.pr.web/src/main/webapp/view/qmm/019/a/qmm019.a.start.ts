module nts.uk.pr.view.qmm019.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.loadListData().done(function() {
            if(screenModel.statementLayoutList().length > 0) {
                let histLength = screenModel.statementLayoutList()[0].history.length;
                if(histLength > 0) {
                    screenModel.currentHistoryId(screenModel.statementLayoutList()[0].history[histLength - 1].historyId);
                }
            }

            __viewContext.bind(screenModel);

            setTimeout(function() {
                $("#A3_4").focus();
            }, 200)
        });
    });
}