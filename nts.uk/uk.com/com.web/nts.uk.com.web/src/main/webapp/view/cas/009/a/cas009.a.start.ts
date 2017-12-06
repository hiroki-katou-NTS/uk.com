module cas009.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage();
        __viewContext.bind(screenModel);
    });
}