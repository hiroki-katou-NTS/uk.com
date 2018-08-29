var kbt002FModel;

module nts.uk.at.view.kbt002.f {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        kbt002FModel = screenModel;
        
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}