module nts.uk.at.view.kbt002.i {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}