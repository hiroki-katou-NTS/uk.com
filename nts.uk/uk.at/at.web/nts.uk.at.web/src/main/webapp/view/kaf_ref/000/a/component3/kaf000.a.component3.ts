module nts.uk.at.view.kaf000_ref.a.component3.viewmodel {

    @component({
        name: 'kaf000-a-component3',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component3/index.html'
    })
    class Kaf000AComponent3ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        prePostAtr: KnockoutObservable<number>;
        prePostAtrDisp: KnockoutObservable<boolean> = ko.observable(false);
        prePostAtrEnable: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.prePostAtr = params.application().prePostAtr;
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.prePostAtr(value.appDispInfoWithDateOutput.prePostAtr);
                vm.prePostAtrDisp(value.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1);
				let appTypeSetting = _.find(value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting, (o: any) => o.appType == vm.appType());
                vm.prePostAtrEnable(appTypeSetting.canClassificationChange);
            });
        }
    }
}