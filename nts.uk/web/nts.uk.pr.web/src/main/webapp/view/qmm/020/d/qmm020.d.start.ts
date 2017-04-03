module qmm020.d {
    __viewContext.ready(function() {
        let screenModel = new qmm020.d.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
    });
}