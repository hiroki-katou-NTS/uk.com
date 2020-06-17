module nts.uk.at.view.kaf000_ref.component1.viewmodel {

    @component({
        name: 'kaf000-component1',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/index1.html'
    })
    class Kaf000Component1ViewModel extends ko.ViewModel {
        prePostAtr: KnockoutObservable<number>;
        appType: number;
        appDate: KnockoutObservable<any>;
        commonSetting: any;
        created(params: any) {
            const vm = this;
            vm.prePostAtr = params.application().prePostAtr;
            vm.appDate = params.application().appDate;
            vm.commonSetting = params.commonSetting;
            vm.commonSetting.subscribe(value => {
                vm.prePostAtr(value.appDispInfoWithDateOutput.prePostAtr);
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}