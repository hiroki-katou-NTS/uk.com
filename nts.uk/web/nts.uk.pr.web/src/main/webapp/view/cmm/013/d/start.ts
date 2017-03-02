__viewContext.ready(function () {
    var screenModel = new cmm013.d.viewmodel.ScreenModel();
    var vm = screenModel;
    screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
});