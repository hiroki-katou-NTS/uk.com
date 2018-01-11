module nts.uk.at.view.kal003.a.daily.fixedcheckcondition {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
        
    }); 
}
