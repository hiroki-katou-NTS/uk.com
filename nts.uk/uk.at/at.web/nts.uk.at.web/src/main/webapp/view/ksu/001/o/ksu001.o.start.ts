module ksu001.o {
    __viewContext.ready(function() {
        var screenModel = new ksu001.o.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}