__viewContext.ready(function () {
    var screenModel = new qmm019.a.ScreenModel();
    screenModel.start(undefined).done(function () {
        __viewContext.bind(screenModel);
        screenModel.bindSortable();
    });
});
//# sourceMappingURL=start.js.map