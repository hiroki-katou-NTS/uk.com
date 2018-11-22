module nts.uk.pr.view.qmm019.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.loadListData().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}