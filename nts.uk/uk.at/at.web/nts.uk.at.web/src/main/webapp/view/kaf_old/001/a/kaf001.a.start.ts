module kaf001.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        _.defer(() => {
            screenModel.applyKCP005ContentSearch([]);
            $('#ccg001component').ntsGroupComponent(screenModel.ccg001ComponentOption);
            $('#kcp005component').ntsListComponent(screenModel.kcp005ComponentOption);
        });
    });
}