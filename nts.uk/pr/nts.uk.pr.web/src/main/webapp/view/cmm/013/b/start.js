__viewContext.ready(function () {
    var screenModel = new cmm013.b.viewmodel.ScreenModel();
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
    //this.bind(screenModel);
});
