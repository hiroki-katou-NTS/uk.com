module nts.uk.at.view.kmk005.f {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmk005.f.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
