module nts.uk.at.view.kfp001.e {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext["viewmodel"] = screenModel;
        __viewContext.bind(screenModel);
        let dataD =   nts.uk.ui.windows.getShared("KFP001_DATAD");
        screenModel.start(dataD);
    });
        }