module qpp008.b {
    __viewContext.ready(function() {
        var screenModel = new qpp008.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}