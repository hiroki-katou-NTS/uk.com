module qpp008.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            nts.uk.ui.confirmSave(screenModel.printSettingDirty);
            __viewContext.bind(screenModel);
        });
    });
}