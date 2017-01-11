__viewContext.ready(function () {
    var screenModel = new cmm013.a.viewmodel.ScreenModel();
    var vm = screenModel;
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
    //this.bind(vm);
});
