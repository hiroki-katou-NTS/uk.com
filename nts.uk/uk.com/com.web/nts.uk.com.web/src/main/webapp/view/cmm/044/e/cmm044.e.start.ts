module cmm044.e {
    __viewContext.ready(function() {
        var screenModel = new cmm044.e.viewmodel.ScreenModel();
            screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}