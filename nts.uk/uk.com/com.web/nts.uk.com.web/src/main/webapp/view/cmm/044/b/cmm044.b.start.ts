module cmm044.b {
    __viewContext.ready(function() {
        var screenModel = new cmm044.b.viewmodel.ScreenModel();
            screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}