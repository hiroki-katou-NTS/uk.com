module qpp008.c {
    __viewContext.ready(function() {
        var screenModel = new qpp008.c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}