module nts.uk.at.view.kaf000_ref.a.component3.viewmodel {

    @component({
        name: 'kaf000-a-component3',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component3/index.html'
    })
    class Kaf000AComponent3ViewModel extends ko.ViewModel {
        prePostAtr: KnockoutObservable<number>;
        appType: number;
        appDispInfoStartupOutput: any;
        created(params: any) {
            const vm = this;
            vm.prePostAtr = params.application().prePostAtr;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.prePostAtr(value.appDispInfoWithDateOutput.prePostAtr);
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}