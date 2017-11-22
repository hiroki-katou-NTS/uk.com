module nts.uk.com.view.ccg025.a {  
    __viewContext.ready(function() {
        let screenModel = new component.viewmodel.ComponentModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel); 
        });        
    }); 
}