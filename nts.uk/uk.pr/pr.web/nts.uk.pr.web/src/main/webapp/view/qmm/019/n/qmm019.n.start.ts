module nts.uk.pr.view.qmm019.n {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#N1_2_container").focus();
        });
    });
}