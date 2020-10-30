module nts.uk.at.view.kmk008.b {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel(0);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}