module nts.uk.pr.view.qui002.b {
    __viewContext.ready(function () {
        let screenModel = new viewmodel.ScreenModel();
        let params = nts.uk.ui.windows.getShared("QUI002_PARAMS_B");
        screenModel.initScreen(params.employeeList).done(()=>{
            __viewContext.bind(screenModel);
            screenModel.loadGird();
            _.defer(() => {
            });
        });
    });
}