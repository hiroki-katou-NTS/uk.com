module nts.uk.at.view.kfp001.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext["viewmodel"] = screenModel;
        __viewContext.bind(screenModel);
        screenModel.start();
    });
        }