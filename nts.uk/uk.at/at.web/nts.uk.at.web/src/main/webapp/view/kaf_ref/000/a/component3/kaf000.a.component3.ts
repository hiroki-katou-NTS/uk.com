module nts.uk.at.view.kaf000_ref.a.component3.viewmodel {

    @component({
        name: 'kaf000-a-component3',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component3/index.html'
    })
    class Kaf000AComponent3ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        prePostAtr: KnockoutObservable<number>;
        prePostAtrDisp: KnockoutObservable<boolean> = ko.observable(false);
        prePostAtrEnable: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.prePostAtr = params.application().prePostAtr;
			params.component3ValidateEvent(vm.validate.bind(vm));
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.prePostAtr(value.appDispInfoWithDateOutput.prePostAtr);
                vm.prePostAtrDisp(value.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1);
                vm.prePostAtrEnable(value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.canClassificationChange);
            });
        }
		
		validate() {
			const vm = this;
			return vm.$validate("#kaf000-a-component3-prePost");
		}
    }
}