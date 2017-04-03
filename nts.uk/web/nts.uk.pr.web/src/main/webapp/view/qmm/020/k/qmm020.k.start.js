__viewContext.ready(function () {
    var screenModel = new qmm020.k.viewmodel.ScreenModel();
    var vm = screenModel;
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
});
//# sourceMappingURL=qmm020.k.start.js.map