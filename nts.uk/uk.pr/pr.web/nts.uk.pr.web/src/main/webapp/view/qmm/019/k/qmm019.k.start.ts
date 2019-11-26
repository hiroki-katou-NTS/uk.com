module nts.uk.pr.view.qmm019.k {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#K1_2").focus();
        });
    });
}