module qpp008.g {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            nts.uk.ui.confirmSave(screenModel.detailDifferentListDirty);
            __viewContext.bind(screenModel);
        });
    });
}