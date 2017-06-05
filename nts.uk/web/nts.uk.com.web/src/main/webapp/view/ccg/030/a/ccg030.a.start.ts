module ccg030.a.start {
    __viewContext.ready(function() {
        var screenModel = new ccg030.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}