module nts.uk.at.view.ksu001.la {
    __viewContext.ready(function () {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function () {
            __viewContext.bind(screenModel);
        });
    });
}