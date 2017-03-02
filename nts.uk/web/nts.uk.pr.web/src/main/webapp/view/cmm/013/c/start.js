__viewContext.ready(function () {
    var screenModel = new cmm013.c.viewmodel.ScreenModel();
    var vm = screenModel;
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
    //this.bind(vm);
});
