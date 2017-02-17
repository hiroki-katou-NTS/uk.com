module qmm006.a {
    __viewContext.ready(function() {
        var screenModel = new qmm006.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}