__viewContext.ready(function () {
    var screenModel = new qpp014.e.ScreenModel();
    screenModel.startPage().done(function () {
        __viewContext.bind(screenModel);
        screenModel.timer.start();
    });
});
//# sourceMappingURL=qpp014.e.start.js.map