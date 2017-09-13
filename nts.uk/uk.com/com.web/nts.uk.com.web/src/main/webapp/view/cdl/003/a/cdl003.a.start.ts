module nts.uk.com.view.cdl003.a {
    
    __viewContext.ready(function() {
        
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res){
            __viewContext.bind(res); 
        });
           
    });
}