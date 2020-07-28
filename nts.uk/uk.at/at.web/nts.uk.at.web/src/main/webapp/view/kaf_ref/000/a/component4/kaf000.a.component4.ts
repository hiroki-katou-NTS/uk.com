module nts.uk.at.view.kaf000_ref.a.component4.viewmodel {
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    
    @component({
        name: 'kaf000-a-component4',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component4/index.html'
    })
    class Kaf000AComponent4ViewModel extends ko.ViewModel {
        appDate: KnockoutObservable<string>;
        appDispInfoStartupOutput: any;
        dispSingleDate: KnockoutObservable<boolean>;
        checkBoxValue: KnockoutObservable<boolean>;
        checkBoxText: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
            vm.appDate = params.application().appDate;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.dispSingleDate = ko.observable(true);
            vm.checkBoxValue = ko.observable(false);
            vm.checkBoxText = ko.observable(vm.$i18n('KAF000_43'));
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                let appType = value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.appType;
                let conditionA8 = (appType == AppType.ABSENCE_APPLICATION || appType == AppType.WORK_CHANGE_APPLICATION || appType == AppType.BUSINESS_TRIP_APPLICATION);
                let conditionA9 = !conditionA8; 
            });
            
            vm.appDate.subscribe(value => {
                vm.$blockui("show");
                let element = '#kaf000-a-component4-singleDate';
                vm.$validate(element)
                .then((valid: boolean) => {
                    if(valid) {
                        let dateLst = [value],
                            appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
                            command = { dateLst, appDispInfoStartupOutput };
                        return vm.$ajax(API.changeAppDate, command);         
                    }  
                }).then((successData: any) => {
                    if(successData) {
                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = successData;
                        vm.appDispInfoStartupOutput.valueHasMutated();
                        CommonProcess.checkUsage(true, element, vm);
                    }
                }).fail((failData: any) => {
                    
                }).always(() => vm.$blockui("hide"));
                                  
            });
            
            vm.checkBoxValue.subscribe(value => {
                const vm = this;
                 
            });
        }
    }
    
    const API = {
        changeAppDate: "at/request/application/changeAppDate"
    }
}