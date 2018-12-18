module nts.uk.pr.view.qmm017.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext['screenModel'] = screenModel;
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            __viewContext['screenModel'].initScreenDTabData();
            $()
        });
    });
}