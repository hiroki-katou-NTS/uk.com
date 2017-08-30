module nts.uk.at.view.kmk009.a {
    __viewContext.ready(function() {
        var screenModel = new kmk009.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        }); 
    });
}