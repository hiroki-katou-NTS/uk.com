module nts.uk.at.view.kmk013.b {
    __viewContext.ready(function() {
        var screenModel = new b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
