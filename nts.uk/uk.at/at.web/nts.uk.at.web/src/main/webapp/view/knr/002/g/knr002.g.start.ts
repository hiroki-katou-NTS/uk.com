module knr002.g {    
    __viewContext.ready(function() {
        var screenModel = new knr002.g.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}