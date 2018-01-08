module nts.uk.at.view.kal003.a.daily {  
    
    __viewContext.ready(function() {
        let daily = new nts.uk.at.view.kal003.a.daily.viewmodel.DailyModel();
        let vm = {
            componentViewmodel: daily       
        }
        vm.componentViewmodel.startPage().done(function() {
            __viewContext.bind(vm); 
        });        
    }); 
}