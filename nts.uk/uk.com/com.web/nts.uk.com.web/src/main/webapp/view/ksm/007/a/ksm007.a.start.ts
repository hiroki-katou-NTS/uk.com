module nts.uk.at.view.ksm007.a {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}