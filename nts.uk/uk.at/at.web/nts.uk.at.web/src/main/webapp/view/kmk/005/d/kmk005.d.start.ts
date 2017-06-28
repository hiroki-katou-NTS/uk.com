module nts.uk.at.view.kmk005.d {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmk005.d.viewmodel.ScreenModel();
        //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        //});
    });
}
