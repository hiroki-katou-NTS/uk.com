module nts.uk.pr.view.qui002.b {
    __viewContext.ready(function () {
        let screenModel = new viewmodel.ScreenModel();
        let params = nts.uk.ui.windows.getShared("QUI002_PARAMS_B");
        screenModel.initScreen(params.employeeList).done(()=>{
            let dialogOptions = {
                forGrid: true,
                headers: [
                    new nts.uk.ui.errors.ErrorHeader("employeeCode", nts.uk.resource.getText('QUI002_B222_2'), "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("employeeName", nts.uk.resource.getText('QUI002_B222_3'), "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("employeeNameBefore", nts.uk.resource.getText('QUI002_B222_4'), "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("message", "エラー内容", "auto", true)
                ]
            };
            __viewContext.bind(screenModel, dialogOptions);
            screenModel.loadGird();
            _.defer(() => {
            });
        });
    });
}