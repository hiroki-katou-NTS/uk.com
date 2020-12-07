module knr002.test {
    
    __viewContext.ready(function() {
        var screenModel = new knr002.test.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });        
    });
}