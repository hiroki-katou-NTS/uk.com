module cmm051.a {
    __viewContext.ready(function() {
        var screenModel = new cmm051.a.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}