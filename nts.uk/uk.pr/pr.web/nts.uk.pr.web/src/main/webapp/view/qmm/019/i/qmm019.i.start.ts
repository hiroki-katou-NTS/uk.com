module nts.uk.pr.view.qmm019.i {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#I1_2_container").focus();
        });
    });
}