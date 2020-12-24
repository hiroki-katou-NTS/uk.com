module nts.uk.at.view.knr002.e {
    
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.knr002.e.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}