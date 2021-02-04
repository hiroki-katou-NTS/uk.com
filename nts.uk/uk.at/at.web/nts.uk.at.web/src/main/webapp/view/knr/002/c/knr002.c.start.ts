module nts.uk.at.view.knr002.c {
    
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.knr002.c.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}