module nts.uk.at.view.kmk013.e {
    __viewContext.ready(function() {
        var screenModel = new e.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);  
        })
    });
}
