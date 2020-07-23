module nts.uk.at.view.kaf000_ref.test.viewmodel {
    
    @bean()
    class Kaf000TestViewModel extends ko.ViewModel {
        
        simpleValue: KnockoutObservable<string>;
        
        created(params: any) {
            const vm = this;
            vm.simpleValue = ko.observable("9658a9f8-398d-443b-a210-10b8f1f3b86f"); 
        }
    
        mounted() {
            
        }
        
        gotoDetail() {
            const vm = this;
            vm.$jump("/view/kaf_ref/000/b/index.xhtml", {
                currentApp: vm.simpleValue(),
                listApp: [vm.simpleValue()]
            });    
        }
    }
    
    const API = {
        
    }
}