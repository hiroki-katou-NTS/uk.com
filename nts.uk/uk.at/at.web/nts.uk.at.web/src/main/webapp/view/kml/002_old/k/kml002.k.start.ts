module kml002.k {
    __viewContext.ready(function() {
        var screenModel = new kml002.k.viewmodel.ScreenModel();
            screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}