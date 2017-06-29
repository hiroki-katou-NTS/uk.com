module nts.uk.at.view.kmk005.g {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmk005.g.viewmodel.ScreenModel();
        screenModel.viewK = new nts.uk.at.view.kmk005.k.viewmodel.ScreenModel() || {};
        //screenModel.startPage().done(function() {
        __viewContext.bind(screenModel);
        //});
    });
}
