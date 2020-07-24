module nts.uk.at.view.kaf000_ref.b.component4.viewmodel {

    @component({
        name: 'kaf000-b-component4',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component4/index.html'
    })
    class Kaf000BComponent4ViewModel extends ko.ViewModel {
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