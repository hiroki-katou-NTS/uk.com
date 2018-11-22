module nts.uk.pr.view.qmm019.h {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#H1_10").focus();
        });
    });
}