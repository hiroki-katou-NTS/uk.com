/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

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
        dispSingleDate: KnockoutObservable<boolean> = ko.observable(true);
        checkBoxValue: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;
            vm.appDate = params.application().appDate;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            params.component4ValidateEvent(vm.validate.bind(vm));

            vm.appDispInfoStartupOutput.subscribe(value => {
                /*let appType = value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.appType;
                let conditionA8 = (appType == AppType.ABSENCE_APPLICATION || appType == AppType.WORK_CHANGE_APPLICATION || appType == AppType.BUSINESS_TRIP_APPLICATION);
                let conditionA9 = !conditionA8;*/ 
            });
            
            vm.appDate.subscribe(value => {
                vm.$blockui("show");
                let element = '#kaf000-a-component4-singleDate',
					appDate = moment(value).format("YYYY/MM/DD");
                vm.$validate(element)
                .then((valid: boolean) => {
                    if(valid) {
                        let dateLst = [appDate],
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

		validate() {
			const vm = this;
			return vm.$validate("#kaf000-a-component4-singleDate")	
		}
    }
    
    const API = {
        changeAppDate: "at/request/application/changeAppDate"
    }
}