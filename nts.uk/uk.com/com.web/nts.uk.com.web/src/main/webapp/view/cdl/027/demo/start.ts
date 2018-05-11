module nts.uk.com.view.cdl027.demo {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
//        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
//        });
    });
    
    class ScreenModel {
        
        constructor() {
            
        }
    }
}
