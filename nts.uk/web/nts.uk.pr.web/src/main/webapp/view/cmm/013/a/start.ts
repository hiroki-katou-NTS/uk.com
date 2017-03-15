module cmm013.a.viewmodel.a.start{
__viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
    
    screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        
       
        });
        
});}