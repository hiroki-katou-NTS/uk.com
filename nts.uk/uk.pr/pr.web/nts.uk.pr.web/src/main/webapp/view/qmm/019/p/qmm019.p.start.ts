module nts.uk.pr.view.qmm019.p {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#P1_3").focus();
        });
    });
}