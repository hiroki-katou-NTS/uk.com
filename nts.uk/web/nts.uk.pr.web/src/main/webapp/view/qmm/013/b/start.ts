module qmm013.b {
    __viewContext.ready(function() {
        var screenModel = new qmm013.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}