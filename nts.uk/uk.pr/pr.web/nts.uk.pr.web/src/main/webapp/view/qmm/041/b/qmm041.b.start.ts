module nts.uk.pr.view.qmm041.b {
    __viewContext.ready(() => {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            $('#B1_4').focus();
        });
    });
}