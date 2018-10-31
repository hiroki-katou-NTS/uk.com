module nts.uk.pr.view.qmm019.m {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#M1_2_container").focus();
        });
    });
}