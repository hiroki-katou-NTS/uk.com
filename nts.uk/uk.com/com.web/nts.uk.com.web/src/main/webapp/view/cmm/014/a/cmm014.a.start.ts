module nts.uk.com.view.cmm014.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            nts.uk.ui.confirmSave(screenModel.dirty);
            __viewContext.bind(screenModel);
        });
    });
}
