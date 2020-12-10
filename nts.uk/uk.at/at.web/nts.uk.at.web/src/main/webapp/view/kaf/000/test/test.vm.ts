module nts.uk.at.view.kaf000.test.viewmodel {
    
    @bean()
    class Kaf000TestViewModel extends ko.ViewModel {
        
        simpleValue: KnockoutObservable<string>;
        
        created(params: any) {
            const vm = this;
            vm.simpleValue = ko.observable("sample"); 
        }
    
        mounted() {
            
        }
        
        gotoDetail() {
            const vm = this;
            vm.$jump("/view/kaf/000/b/index.xhtml", {
                currentApp: vm.simpleValue(),
                listAppMeta: [vm.simpleValue()]
            });    
        }
    }
    
    const API = {
        
    }
}