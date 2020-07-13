module nts.uk.at.view.kaf000_ref.b.component2.viewmodel {

    @component({
        name: 'kaf000-b-component2',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component2/index.html'
    })
    class Kaf000BComponent2ViewModel extends ko.ViewModel {
        appType: number;
        appDispInfoStartupOutput: any;
        opReversionReason: KnockoutObservable<string>;
        created(params: any) {
            const vm = this; 
            vm.opReversionReason = ko.observable("opReversionReason");
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
        }
    
        mounted() {
            const vm = this;
        }
    }
}