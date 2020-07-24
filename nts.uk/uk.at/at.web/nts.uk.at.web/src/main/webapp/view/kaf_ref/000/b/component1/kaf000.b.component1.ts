module nts.uk.at.view.kaf000_ref.b.component1.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    
    @component({
        name: 'kaf000-b-component1',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component1/index.html'
    })
    class Kaf000BComponent1ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        message: KnockoutObservable<string>; 
        deadline: KnockoutObservable<string>;
        displayArea: KnockoutObservable<boolean>; 
        displayMsg: KnockoutObservable<boolean>;
        displayDeadline: KnockoutObservable<boolean>;
        
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.message = ko.observable("line111111111111111111");
            vm.deadline = ko.observable("line222222222222222222");
            vm.displayArea = ko.pureComputed(() => {
                return vm.displayMsg() && vm.displayDeadline();
            });
            vm.displayMsg = ko.observable(false);
            vm.displayDeadline = ko.observable(false);
            
            CommonProcess.initDeadlineMsg(vm.appDispInfoStartupOutput(), vm);
        }
    
        mounted() {
            const vm = this;
        }
    }
}