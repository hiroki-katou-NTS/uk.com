module nts.uk.pr.view.qmm035.a {
    __viewContext.ready(function () {
        let screenModel = new nts.uk.pr.view.qmm035.a.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        screenModel.setTabIndex();
    });
}