/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000_ref.a.component4.viewmodel {
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    
    @component({
        name: 'kaf000-a-component4',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component4/index.html'
    })
    class Kaf000AComponent4ViewModel extends ko.ViewModel {
        appType: number = null;
        appDate: KnockoutObservable<string>;
        dateValue: KnockoutObservable<any>;
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        dispSingleDate: KnockoutObservable<boolean> = ko.observable(true);
        checkBoxValue: KnockoutObservable<boolean> = ko.observable(false);
        dispCheckBox: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;
            vm.appDate = ko.observable("");
            vm.dateValue = ko.observable({});
            
            vm.application = params.application;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;

            vm.appDispInfoStartupOutput.subscribe(value => {
                if(!vm.appType){
                    vm.appType = value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting[0].appType;
                    if (vm.appType == AppType.ABSENCE_APPLICATION || vm.appType == AppType.WORK_CHANGE_APPLICATION || vm.appType == AppType.BUSINESS_TRIP_APPLICATION) {
                        vm.dispCheckBox(true);
                        vm.dispSingleDate(true);
                        vm.checkBoxValue.subscribe(value => {
                            if(value) {
                                vm.dispSingleDate(false);
                                nts.uk.ui.errors.clearAll();
                                // vm.$errors("clear", ['#kaf000-a-component4-singleDate']);
                            } else {
                                vm.dispSingleDate(true);
                                nts.uk.ui.errors.clearAll();
                                // vm.$errors("clear", ['#kaf000-a-component4-rangeDate']);
                                vm.application().opAppStartDate(moment(vm.appDate()).format("YYYY/MM/DD"));
                                vm.application().opAppEndDate(moment(vm.appDate()).format("YYYY/MM/DD"));
                            }
                        });
                    }
                }
            });

            vm.appDate.subscribe(value => {
            	if(vm.checkBoxValue()) {
            		return;	
            	}
                vm.$blockui("show");
                let element = '#kaf000-a-component4-singleDate',
                    appDate = moment(value).format("YYYY/MM/DD");
                vm.$validate(element)
                .then((valid: boolean) => {
                    if(valid) {
                        let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
                            command = { appDate, appDispInfoStartupOutput };
                        return vm.$ajax(API.changeAppDate, command);         
                    }  
                }).then((successData: any) => {
                    if(successData) {
						let applicationJS = ko.toJS(vm.application);
                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = successData;
                        vm.appDispInfoStartupOutput.valueHasMutated();
						vm.application().prePostAtr(applicationJS.prePostAtr);
						vm.application().opAppStandardReasonCD(applicationJS.opAppStandardReasonCD);
                        vm.dateValue().startDate = appDate;
                        vm.dateValue().endDate = appDate;
                        vm.dateValue.valueHasMutated();
                        vm.application().appDate(appDate);
                        vm.application().opAppStartDate(appDate);
                        vm.application().opAppEndDate(appDate);
                        CommonProcess.checkUsage(true, element, vm);
                    }
                }).fail((res: any) => {
                 	if (res.messageId == "Msg_426") {
	                    vm.$dialog.error({ messageId: "Msg_426" }).then(() => {
	                        vm.$jump("com", "/view/ccg/008/a/index.xhtml"); 
	                    });    
	                } else {
	                    vm.$dialog.error(res.message).then(() => {
	                        vm.$jump("com", "/view/ccg/008/a/index.xhtml"); 
	                    }); 
	                }   
                }).always(() => vm.$blockui("hide"));
                                  
            });

            vm.dateValue.subscribe(value => {
            	if(!vm.checkBoxValue()) {
            		return;
                }
                vm.$blockui("show");
                let element = '#kaf000-a-component4-rangeDate .nts-input',
                    startDate = moment(value.startDate).format('YYYY/MM/DD'),
                    endDate = moment(value.endDate).format('YYYY/MM/DD');
	       		vm.$validate(element).then((valid: boolean) => {
                    if(valid) {
                    	return vm.$validate('#kaf000-a-component4-rangeDate');	
                    }
                }).then((valid: any) => {
                	if(valid) {
                		let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
                            command = { startDate, endDate, appDispInfoStartupOutput };
                        return vm.$ajax(API.changeAppDate, command);	
                	}
                }).then((successData: any) => {
                    if(successData) {
						let applicationJS = ko.toJS(vm.application);
                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = successData;
                        vm.appDispInfoStartupOutput.valueHasMutated();
						vm.application().prePostAtr(applicationJS.prePostAtr);
						vm.application().opAppStandardReasonCD(applicationJS.opAppStandardReasonCD);
                        vm.appDate(startDate);
                        vm.application().appDate(startDate);
                        vm.application().opAppStartDate(startDate);
                        vm.application().opAppEndDate(endDate);
                        CommonProcess.checkUsage(true, element, vm);
                    }
                }).fail((res: any) => {
                	if (res.messageId == "Msg_426") {
	                    vm.$dialog.error({ messageId: "Msg_426" }).then(() => {
	                        vm.$jump("com", "/view/ccg/008/a/index.xhtml"); 
	                    });    
	                } else {
	                    vm.$dialog.error(res.message).then(() => {
	                        vm.$jump("com", "/view/ccg/008/a/index.xhtml"); 
	                    }); 
	                }       
                }).always(() => vm.$blockui("hide"));
            });
        }
    }
    
    const API = {
        changeAppDate: "at/request/application/changeAppDate"
    }
}