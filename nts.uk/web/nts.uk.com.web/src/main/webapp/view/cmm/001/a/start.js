__viewContext.ready(function () {
    var screenModel = new cmm001.a.ScreenModel();
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
});
