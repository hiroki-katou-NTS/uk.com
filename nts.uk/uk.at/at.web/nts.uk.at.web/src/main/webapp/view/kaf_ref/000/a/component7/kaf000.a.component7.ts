module nts.uk.at.view.kaf000_ref.a.component7.viewmodel {

    @component({
        name: 'kaf000-a-component7',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component7/index.html'
    })
    class Kaf000AComponent7ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        checkBoxValue: KnockoutObservable<boolean>;
        dispCheckBox: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.checkBoxValue = ko.observable(false);
            vm.dispCheckBox = ko.observable(true);
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.dispCheckBox(!value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.sendMailWhenRegister &&
                                    value.appDispInfoNoDateOutput.mailServerSet);
                if(vm.dispCheckBox()) {
                    vm.checkBoxValue(value.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.manualSendMailAtr);            
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