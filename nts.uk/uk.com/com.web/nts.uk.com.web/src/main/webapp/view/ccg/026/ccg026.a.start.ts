module nts.uk.com.view.ccg026.a {  
    
    __viewContext.ready(function() {
        let component = new nts.uk.com.view.ccg026.a.component.viewmodel.ComponentModel({ 
            roleId: 'abcs',
            classification: 1,
            maxRow: 3
        });
        let vm = {
            componentViewmodel: component    
        }
        vm.componentViewmodel.startPage().done(function() {
            __viewContext.bind(vm); 
        });        
    }); 
}