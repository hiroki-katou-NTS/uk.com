module qmm020.b {
    __viewContext.ready(function() {
        let screenModel = new qmm020.b.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        })
    });

}