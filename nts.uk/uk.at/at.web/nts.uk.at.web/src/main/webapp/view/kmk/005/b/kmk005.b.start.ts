module nts.uk.at.view.kmk005.b {
    __viewContext.ready(function() {
        var screenModel = new b.viewmodel.ScreenModel();
        screenModel.startPage();
       //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
       //});
    });
}
