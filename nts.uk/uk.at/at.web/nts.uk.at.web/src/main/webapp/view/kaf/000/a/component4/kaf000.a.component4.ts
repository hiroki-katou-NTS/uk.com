/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000.a.component4.viewmodel {
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;

    @component({
        name: 'kaf000-a-component4',
        template: `
            <div id="kaf000-a-component4">
                <div class="table item">
                    <div class="cell cm-column valign-center"  style="width: 120px;">
                        <div class="cell valign-center" data-bind="ntsFormLabel:{ required:true }, text: $i18n('KAF000_49')"></div>
                    </div>
                    <div class="cell valign-center">
                        <div class="table">
                            <div class="cell valign-top" data-bind="if: dispCheckBox">
                                <div style="margin-left: -3px" data-bind="i18n: 'KAF000_50', ntsCheckBox: { checked: checkBoxValue }"></div>
                            </div>
                            <div class="cell valign-top" data-bind="if: dispSingleDate">
                                <div id="kaf000-a-component4-singleDate"
                                    data-bind="ntsDatePicker: {name: $i18n('KAF000_49'),
                                                            required: true,
                                                            value: appDate }">
                                </div>
                            </div>
                            <div class="cell valign-top" data-bind="if: !dispSingleDate()">
                                <div id="kaf000-a-component4-rangeDate"
                                    data-bind="ntsDateRangePicker: {
                                                            name: $i18n('KAF000_49'),
                                                            startName: $i18n('KAF000_49'),
                                                            endName: $i18n('KAF000_49'),
                                                            required: true,
                                                            value: dateValue, 
                                                            showNextPrevious: false }">
                                </div>
                            </div>
                            <div data-bind="if: !dispSingleDate() && from006">
                                <div class="pl-15 cell valign-center" data-bind=" text: $i18n('KAF006_101')"></div>
                            </div>
                        </div>
                    </div>
                    <div id="comment1" class="cell valign-top" style="vertical-align: middle; padding-left: 10px" data-bind="text: $vm.comment1, visible: $vm.application().appType === 2"></div>
                </div>
            </div>
        `
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
        checkAppDate: KnockoutObservable<boolean>;
        from006: boolean;
		opOvertimeAppAtr: KnockoutObservable<number>;

        created(params: any) {
            const vm = this;
            vm.appDate = ko.observable("");
            vm.dateValue = ko.observable({});
            vm.application = params.application;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.checkAppDate = params.checkAppDate ? params.checkAppDate : ko.observable(true);
            vm.from006 = params.from006;
			vm.opOvertimeAppAtr = params.opOvertimeAppAtr ? params.opOvertimeAppAtr : ko.observable(null);

			if (!_.isEmpty(vm.application().appDate())) {
				vm.appDate(vm.application().appDate());
			}

            vm.appDispInfoStartupOutput.subscribe((value: any) => {
                if(!vm.appType){
                    vm.appType = value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting[0].appType;
                    if (vm.appType == AppType.ABSENCE_APPLICATION || vm.appType == AppType.WORK_CHANGE_APPLICATION || vm.appType == AppType.BUSINESS_TRIP_APPLICATION) {
                        vm.dispCheckBox(true);
                        vm.dispSingleDate(true);
						vm.checkBoxValue.subscribe(oldValue => {
							$('#kaf000-a-component4-singleDate').ntsError('clear');
							$('#kaf000-a-component4-rangeDate .nts-input').ntsError('clear');
						}, null, "beforeChange");
                        vm.checkBoxValue.subscribe(value => {
                            if(value) {
                                vm.dispSingleDate(false);
                            } else {
                                vm.dispSingleDate(true);
                                if(_.isEmpty(vm.appDate())) {
                                    vm.application().opAppStartDate('');
                                    vm.application().opAppEndDate('');
                                } else {
                                    vm.application().opAppStartDate(moment(vm.appDate()).format("YYYY/MM/DD"));
                                    vm.application().opAppEndDate(moment(vm.appDate()).format("YYYY/MM/DD"));
                                }
                                
                            }
                        });
                    }
                }
            });
        }

		mounted() {
			const vm = this;
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
							opOvertimeAppAtr = vm.opOvertimeAppAtr(),
                            command = { appDate, appDispInfoStartupOutput, opOvertimeAppAtr };
                        vm.checkAppDate(true);
                        return vm.$ajax(API.changeAppDate, command);
                    }else {
                        vm.checkAppDate(false);
                    }
                }).then((successData: any) => {
                    if(successData) {
						let applicationJS = ko.toJS(vm.application);
                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = successData;
                        vm.appDispInfoStartupOutput.valueHasMutated();
						if(applicationJS.opStampRequestMode==1) {
							vm.application().prePostAtr(1);
						} else {
							if(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1) {
								vm.application().prePostAtr(applicationJS.prePostAtr);
							} else {
								vm.application().prePostAtr(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.prePostAtr);
							}
						}
						vm.application().opAppStandardReasonCD(applicationJS.opAppStandardReasonCD);
                        vm.dateValue().startDate = appDate;
                        vm.dateValue().endDate = appDate;
                        vm.dateValue.valueHasMutated();
                        vm.application().appDate(appDate);
                        vm.application().opAppStartDate(appDate);
                        vm.application().opAppEndDate(appDate);
                        vm.application.valueHasMutated();
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
                        vm.checkAppDate(true);
						if(vm.appType==AppType.BUSINESS_TRIP_APPLICATION) {
							if(moment(endDate).diff(startDate, 'days') > 30) {
								vm.application().appDate(startDate);
		                        vm.application().opAppStartDate(startDate);
		                        vm.application().opAppEndDate(endDate);
		                        vm.application.valueHasMutated();
								return false;
							}
						}
                    	return vm.$validate('#kaf000-a-component4-rangeDate');
                    }else {
                        vm.checkAppDate(false);
                    }
                }).then((valid: any) => {
                	if(valid) {
                		let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
							opOvertimeAppAtr = vm.opOvertimeAppAtr(),
                            command = { startDate, endDate, appDispInfoStartupOutput, opOvertimeAppAtr };
                        return vm.$ajax(API.changeAppDate, command);
                	} else {
                        vm.application().appDate("");
                    }
                }).then((successData: any) => {
                    if(successData) {
						let applicationJS = ko.toJS(vm.application);
                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = successData;
                        vm.appDispInfoStartupOutput.valueHasMutated();
						if(applicationJS.opStampRequestMode==1) {
							vm.application().prePostAtr(1);
						} else {
							if(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1) {
								vm.application().prePostAtr(applicationJS.prePostAtr);
							} else {
								vm.application().prePostAtr(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.prePostAtr);
							}
						}
						vm.application().opAppStandardReasonCD(applicationJS.opAppStandardReasonCD);
                        vm.appDate(startDate);
                        vm.application().appDate(startDate);
                        vm.application().opAppStartDate(startDate);
                        vm.application().opAppEndDate(endDate);
                        vm.application.valueHasMutated();
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