module ksu001.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        var screenModel = new ksu001.a.viewmodel.ScreenModel();

        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}