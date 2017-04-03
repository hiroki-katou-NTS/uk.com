module qmm020.i {
    __viewContext.ready(function() {
        var screenModel = new qmm020.e.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}