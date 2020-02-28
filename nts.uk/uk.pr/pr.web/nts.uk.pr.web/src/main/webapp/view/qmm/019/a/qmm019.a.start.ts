module nts.uk.pr.view.qmm019.a {
    import StatementLayoutHistData = nts.uk.pr.view.qmm019.a.viewmodel.StatementLayoutHistData;
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext['screenModel'] = screenModel;
        screenModel.loadListData().done(function() {
            screenModel.statementLayoutHistData(new StatementLayoutHistData(null, false));

            if(screenModel.statementLayoutList().length > 0) {
                let histLength = screenModel.statementLayoutList()[0].history.length;
                if(histLength > 0) {
                    screenModel.currentHistoryId(screenModel.statementLayoutList()[0].history[0].historyId);
                }
            } else {
                screenModel.createIfEmpty();
            }

            __viewContext.bind(screenModel);

            setTimeout(function() {
                $("#A3_4").focus();

                if (/Edge/.test(navigator.userAgent)) {
                    $("#treegrid1_scroll").addClass("tree-grid-edge");
                } else if (/Chrome/.test(navigator.userAgent)) {
                    $("#treegrid1_scroll").addClass("tree-grid-chrome");
                } else {
                    $("#treegrid1_scroll").addClass("tree-grid-ie");
                }
            }, 200)
        });
    });
}