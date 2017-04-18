module qpp008.g {
    __viewContext.ready(function() {
        var screenModel = new qpp008.g.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}