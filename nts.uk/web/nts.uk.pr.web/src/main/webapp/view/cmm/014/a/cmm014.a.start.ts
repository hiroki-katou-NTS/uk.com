module cmm014.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        nts.uk.ui.confirmSave(screenModel.dirty_1);
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}