module kal004.a {
    __viewContext.ready(function() {
        let screenModel = new model.ScreenModel();
        screenModel.startPage();
        __viewContext.bind(screenModel);
    });
}