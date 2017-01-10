module qrm001.b {
    __viewContext.ready(function() {
        var screenModel = new qrm001.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}