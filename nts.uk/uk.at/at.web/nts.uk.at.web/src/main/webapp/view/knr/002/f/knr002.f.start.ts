module knr002.f {    
    __viewContext.ready(function() {
        var screenModel = new knr002.f.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}