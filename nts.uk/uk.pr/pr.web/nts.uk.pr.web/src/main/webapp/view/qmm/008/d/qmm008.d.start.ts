module nts.uk.pr.view.qmm008.d {
    __viewContext.ready(function () {
        let screenModel = new nts.uk.pr.view.qmm008.d.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        screenModel.setTabIndex();
    });
}