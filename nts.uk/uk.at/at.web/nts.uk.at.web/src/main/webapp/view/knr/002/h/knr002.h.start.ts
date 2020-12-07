module knr002.h {    
    __viewContext.ready(function() {
        var screenModel = new knr002.h.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}