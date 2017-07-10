module cmm044.g {
    __viewContext.ready(function() {
        var screenModel = new cmm044.g.viewmodel.ScreenModel();
        screenModel.start();
         __viewContext.bind(screenModel);
    });
}