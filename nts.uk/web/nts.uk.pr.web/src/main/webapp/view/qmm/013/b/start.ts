module qmm013.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage();
        this.bind(screenModel);
    });
}