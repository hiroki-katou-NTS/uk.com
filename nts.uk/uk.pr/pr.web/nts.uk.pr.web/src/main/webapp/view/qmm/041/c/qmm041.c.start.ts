module nts.uk.pr.view.qmm041.c {
    __viewContext.ready(() => {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            $('#C1_3').focus();
        });
    });
}