module ksu001.a {
    __viewContext.ready(function() {
        var screenModel = new ksu001.a.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}