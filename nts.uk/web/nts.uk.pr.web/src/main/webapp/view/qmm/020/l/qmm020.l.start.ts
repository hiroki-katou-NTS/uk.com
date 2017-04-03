__viewContext.ready(function () {
    var screenModel = new qmm020.l.viewmodel.ScreenModel();
    var vm = screenModel;
    screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
});