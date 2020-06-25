module nts.uk.at.view.kaf009_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import Model = nts.uk.at.view.kaf009_ref.shr.viewmodel.Model;
    
    @bean()
    class Kaf009BViewModel extends ko.ViewModel {
        
        application: KnockoutObservable<Application>;
        model: KnockoutObservable<Model>;
        commonSetting: any;
    
        created(params: any) {
            const vm = this;
            vm.application = ko.observable(new Application("", 1, 2, ""));   
            vm.commonSetting = ko.observable(CommonProcess.initCommonSetting());
            vm.model = ko.observable(new Model(true, true, '01', 'ddd','30','33'));
        }
    
        mounted() {
 
        }
        
        register() {
            const vm = this;
            console.log(ko.toJS(vm.application()));
            console.log(ko.toJS(vm.model()));
            vm.model(new Model(true, true, '01', 'hoang','30','33'));
        }
    }
    
    const API = {
        startNew: "at/request/application/workchange/startNew"
    }
}