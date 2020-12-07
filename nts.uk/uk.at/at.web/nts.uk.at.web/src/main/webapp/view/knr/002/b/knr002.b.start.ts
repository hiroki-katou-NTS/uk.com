module knr002.b {    
    __viewContext.ready(function() {
        var screenModel = new knr002.b.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}