module nts.uk.com.view.cli001.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => screenModel.setInitialFocus());
        });
    });
}