module qmm020.c {
    __viewContext.ready(function() {
        let screenModel = new qmm020.c.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        })

    });
}