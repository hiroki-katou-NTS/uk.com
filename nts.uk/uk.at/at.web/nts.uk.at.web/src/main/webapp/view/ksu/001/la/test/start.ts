module test {
    __viewContext.ready(function () {
        let screenModel = new viewmodelTest.ScreenModel();
        screenModel.startPage().done(function () {
            __viewContext.bind(screenModel);
        });
    });
}