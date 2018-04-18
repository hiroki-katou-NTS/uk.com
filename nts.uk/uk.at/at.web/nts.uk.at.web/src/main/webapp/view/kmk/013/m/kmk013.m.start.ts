module nts.uk.at.view.kmk013.m {
    __viewContext.ready(function() {
        var screenModel = new m.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
