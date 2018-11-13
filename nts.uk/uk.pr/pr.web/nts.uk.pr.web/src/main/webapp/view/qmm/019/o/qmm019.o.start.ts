module nts.uk.pr.view.qmm019.o {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#O1_2_container").focus();
        });
    });
}