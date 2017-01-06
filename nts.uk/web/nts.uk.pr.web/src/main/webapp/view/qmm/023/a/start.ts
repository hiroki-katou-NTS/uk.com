module qmm023.a {
    __viewContext.ready(function() {
        var screenModel = new qmm023.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}