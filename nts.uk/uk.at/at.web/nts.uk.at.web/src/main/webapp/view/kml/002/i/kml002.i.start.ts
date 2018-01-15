module kml002.i {
    __viewContext.ready(function() {
        var screenModel = new kml002.i.viewmodel.ScreenModel();
            screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}