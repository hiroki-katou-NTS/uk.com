module nts.uk.at.view.kaf000_ref.b.component1.viewmodel {

    @component({
        name: 'kaf000-b-component1',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component1/index.html'
    })
    class Kaf000BComponent1ViewModel extends ko.ViewModel {
        appType: number;
        appDispInfoStartupOutput: any;
        created(params: any) {
            const vm = this; 
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
        }
    
        mounted() {
            const vm = this;
        }
    }
}