module nts.uk.at.view.kaf004.b {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kaf004.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
        
    });
}