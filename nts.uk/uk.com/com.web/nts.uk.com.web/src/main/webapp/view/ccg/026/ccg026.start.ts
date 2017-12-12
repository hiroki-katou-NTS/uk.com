module nts.uk.com.view.ccg026 {
    __viewContext.ready(function() {
        let component = new nts.uk.com.view.ccg026.component.viewmodel.ComponentModel({ 
            roleId: 'abcs',
            classification: 1,
            maxRow: 2
        });
        let vm = {
            componentViewmodel: component
        }
        vm.componentViewmodel.startPage().done(function() {
            __viewContext.bind(vm); 
        });
    }); 
}