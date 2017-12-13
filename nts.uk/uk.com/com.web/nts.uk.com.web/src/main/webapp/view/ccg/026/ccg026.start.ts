module nts.uk.com.view.ccg026 {
    __viewContext.ready(function() {
        let component = new nts.uk.com.view.ccg026.component.viewmodel.ComponentModel({ 
            roleId: '00000000-0000-0000-0000-000000000001',
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