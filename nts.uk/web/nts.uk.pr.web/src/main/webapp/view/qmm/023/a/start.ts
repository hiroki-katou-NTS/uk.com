module qmm023.a.start {
    __viewContext.ready(function() {
        var screenModel = new qmm023.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}