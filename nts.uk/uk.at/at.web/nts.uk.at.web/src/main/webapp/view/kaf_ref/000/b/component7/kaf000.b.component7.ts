module nts.uk.at.view.kaf000_ref.b.component7.viewmodel {

    @component({
        name: 'kaf000-b-component7',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component7/index.html'
    })
    class Kaf000BComponent7ViewModel extends ko.ViewModel {
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
            vm.reasonTypeItemLst = ko.observableArray([]);
            vm.appReasonCDRequired = ko.observable(false);
            vm.appReasonRequired = ko.observable(false);
            vm.appReasonCDDisp = ko.observable(false);
            vm.appReasonDisp = ko.observable(false);
            
            vm.appReasonCDRequired(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired);
            vm.appReasonRequired(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason);    
            vm.appReasonCDDisp(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.displayStandardReason == 1);
            vm.appReasonDisp(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.displayAppReason == 1);
            vm.reasonTypeItemLst(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.reasonTypeItemLst);
            vm.opAppStandardReasonCD(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStandardReasonCD);
			let initReasonTypeItem = _.find(vm.reasonTypeItemLst(), (o) => o.appStandardReasonCD == vm.opAppStandardReasonCD());
			if(_.isUndefined(initReasonTypeItem)) {
				let dataLst = [{
		            appStandardReasonCD: '',
		            displayOrder: 0,
		            defaultValue: false,
		            opReasonForFixedForm: vm.$i18n('KAFS00_23'),   
		        }];
				vm.reasonTypeItemLst(_.concat(dataLst, vm.reasonTypeItemLst()));
				vm.opAppStandardReasonCD(_.head(vm.reasonTypeItemLst()).appStandardReasonCD);
			}
            vm.opAppReason(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppReason);   
        }
    
        mounted() {
            const vm = this;
        }
    }
}