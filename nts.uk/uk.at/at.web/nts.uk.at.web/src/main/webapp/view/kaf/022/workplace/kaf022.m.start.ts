module nts.uk.at.view.kaf022.m {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        screenModel.reloadData();
    });
}