module nts.uk.at.view.kmk013.k {
    __viewContext.ready(function() {
        var screenModel = new k.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
