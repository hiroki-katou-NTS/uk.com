module nts.uk.at.view.kaf000_ref.a.component2.viewmodel {

    @component({
        name: 'kaf000-a-component2',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component2/index.html'
    })
    class Kaf000AComponent2ViewModel extends ko.ViewModel {
        appType: number;
        appDispInfoStartupOutput: any;
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.appDispInfoStartupOutput.subscribe(value => {
                
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}