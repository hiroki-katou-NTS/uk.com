__viewContext.ready(function () {
    var screenModel = new qmm019.g.viewmodel.ScreenModel();
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
});
