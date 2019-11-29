module nts.uk.pr.view.qmm041.d {
    __viewContext.ready(() => {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            $('#D2_6').focus();
        });
    });
}