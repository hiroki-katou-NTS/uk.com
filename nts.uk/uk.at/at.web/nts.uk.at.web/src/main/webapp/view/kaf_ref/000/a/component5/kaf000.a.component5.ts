module nts.uk.at.view.kaf000_ref.a.component5.viewmodel {

    @component({
        name: 'kaf000-a-component5',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component5/index.html'
    })
    class Kaf000AComponent2ViewModel extends ko.ViewModel {
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