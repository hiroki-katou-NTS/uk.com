module nts.uk.at.view.kaf000_ref.a.component7.viewmodel {

    @component({
        name: 'kaf000-a-component7',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component7/index.html'
    })
    class Kaf000AComponent7ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        checkBoxValue: KnockoutObservable<boolean>;
        dispCheckBox: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.checkBoxValue = params.checkBoxValue;
            vm.dispCheckBox = ko.observable(true);
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.dispCheckBox(!value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.sendMailWhenRegister &&
                                    value.appDispInfoNoDateOutput.mailServerSet);
                if(vm.dispCheckBox()) {
                    vm.checkBoxValue(value.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.manualSendMailAtr==1);            
                } else {
                    vm.checkBoxValue(false);
                }
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}