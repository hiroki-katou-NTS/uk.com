module nts.uk.at.view.kaf000_ref.b.component2.viewmodel {

    @component({
        name: 'kaf000-b-component2',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component2/index.html'
    })
    class Kaf000BComponent2ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        opReversionReason: KnockoutObservable<string>;
        opReversionReasonDisp: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this; 
            vm.opReversionReason = ko.observable("opReversionReason");
            vm.opReversionReasonDisp = ko.observable(false);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput();
            
            vm.opReversionReason(vm.appDispInfoStartupOutput.appDetailScreenInfo.application.opReversionReason);
            vm.opReversionReasonDisp(!_.isEmpty(vm.appDispInfoStartupOutput.appDetailScreenInfo.application.opReversionReason));
            if (vm.opReversionReasonDisp()) {
                let opReversionReason = vm.appDispInfoStartupOutput.appDetailScreenInfo.application.opReversionReason;
                vm.opReversionReason(opReversionReason.replace(/\n/g, "\<br/>"));
            }
        }
    
        mounted() {
            const vm = this;
        }
    }
}