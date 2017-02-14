module qmm013.a {
    __viewContext.ready(function() {
        var screenModel = new qmm013.a.viewmodel.ScreenModel();
        screenModel.startPage();
        this.bind(screenModel);
    });
}