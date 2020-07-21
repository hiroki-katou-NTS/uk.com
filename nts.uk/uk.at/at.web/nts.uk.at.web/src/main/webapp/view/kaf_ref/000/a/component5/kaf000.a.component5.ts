module nts.uk.at.view.kaf000_ref.a.component5.viewmodel {

    @component({
        name: 'kaf000-a-component5',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component5/index.html'
    })
    class Kaf000AComponent5ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opAppReason: KnockoutObservable<string>;
        reasonTypeItemLst: KnockoutObservableArray<any>;
        appReasonCDRequired: KnockoutObservable<boolean>;
        appReasonRequired: KnockoutObservable<boolean>;
        appReasonCDDisp: KnockoutObservable<boolean>;  
        appReasonDisp: KnockoutObservable<boolean>; 
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.opAppStandardReasonCD = params.application().opAppStandardReasonCD;
            vm.opAppReason = params.application().opAppReason;
            vm.reasonTypeItemLst = ko.observableArray([{appStandardReasonCD: 0, opReasonForFixedForm: "test"}]);
            vm.appReasonCDRequired = ko.observable(false);
            vm.appReasonRequired = ko.observable(false);
            vm.appReasonCDDisp = ko.observable(false);
            vm.appReasonDisp = ko.observable(false);
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.appReasonCDRequired(value.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired);
                vm.appReasonRequired(value.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason);    
                vm.appReasonCDDisp(value.appDispInfoNoDateOutput.displayStandardReason == 1);
                vm.appReasonDisp(value.appDispInfoNoDateOutput.displayAppReason == 1);
                vm.reasonTypeItemLst(value.appDispInfoNoDateOutput.reasonTypeItemLst);
                let defaultReasonTypeItem = _.find(value.appDispInfoNoDateOutput.reasonTypeItemLst, (o) => o.defaultValue);
                if(_.isUndefined(defaultReasonTypeItem)) {
                    vm.opAppStandardReasonCD(_.head(value.appDispInfoNoDateOutput.reasonTypeItemLst).appStandardReasonCD);
                } else {
                    vm.opAppStandardReasonCD(defaultReasonTypeItem.appStandardReasonCD);        
                }
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}