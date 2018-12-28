module nts.uk.pr.view.qmm019.l {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#L1_2").focus();
        });
    });
}