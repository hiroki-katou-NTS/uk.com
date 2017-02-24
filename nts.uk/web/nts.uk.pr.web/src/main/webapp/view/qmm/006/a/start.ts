module qmm006.a {
    __viewContext.ready(function() {
        var screenModel = new qmm006.a.viewmodel.ScreenModel();
        nts.uk.ui.confirmSave(screenModel.dirty);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);

        });
    });
}