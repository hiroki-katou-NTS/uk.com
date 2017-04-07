module qpp014.b {
    __viewContext.ready(function() {
        var screenModel = new qpp014.b.viewmodel.ScreenModel();

        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}