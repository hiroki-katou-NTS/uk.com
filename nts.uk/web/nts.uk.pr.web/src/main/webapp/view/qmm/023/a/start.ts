module qmm023.a.start {
    __viewContext.ready(function() {
        var screenModel = new qmm023.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            nts.uk.ui.confirmSave(screenModel.currentTaxDirty);
            __viewContext.bind(screenModel);
        });
    });
}