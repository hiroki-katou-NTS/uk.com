module kml002.l {
    __viewContext.ready(function() {
        var screenModel = new kml002.l.viewmodel.ScreenModel();
            screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}