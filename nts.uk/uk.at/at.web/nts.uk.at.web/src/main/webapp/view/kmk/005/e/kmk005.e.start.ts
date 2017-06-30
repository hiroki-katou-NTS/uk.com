module nts.uk.at.view.kmk005.e {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmk005.e.viewmodel.ScreenModel();
        //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        //});
    });
}
