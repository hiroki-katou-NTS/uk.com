/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;
import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
import WorkType = nts.uk.at.view.kaf006.shr.viewmodel.WorkType;
import Kaf006ShrViewModel = nts.uk.at.view.kaf006.shr.viewmodel.Kaf006ShrViewModel;

module nts.uk.at.view.kaf006_ref.a.viewmodel {

    @bean()
    export class Kaf006AViewModel extends Kaf000AViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.ABSENCE_APPLICATION);
        isAgentMode : KnockoutObservable<boolean> = ko.observable(false);
        isSendMail: KnockoutObservable<Boolean> = ko.observable(false);
		application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
		data: any = null;
		hdAppSet: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedType: KnockoutObservable<any> = ko.observable();
		workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedWorkTypeCD: KnockoutObservable<string> = ko.observable(null);
		selectedWorkType: KnockoutObservable<WorkType> = ko.observable(new WorkType({workTypeCode: '', name: ''}));
		dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedDateSpec: KnockoutObservable<any> = ko.observable();
		maxNumberOfDay: KnockoutObservable<any> = ko.observable();
		specAbsenceDispInfo: KnockoutObservable<any> = ko.observable();
		isDispMourn: any = ko.observable(false);
		isCheckMourn: any = ko.observable(true);
		requiredVacationTime: KnockoutObservable<number> = ko.observable(1200);
		timeRequired: KnockoutObservable<string> = ko.observable();

		yearRemain: KnockoutObservable<number> = ko.observable();
		subHdRemain: KnockoutObservable<number> = ko.observable();
		subVacaRemain: KnockoutObservable<number> = ko.observable();
		remainingHours: KnockoutObservable<number> = ko.observable();

		over60HHourRemain: KnockoutObservable<string> = ko.observable();
		subVacaHourRemain: KnockoutObservable<string> = ko.observable();
		timeYearLeave: KnockoutObservable<string> = ko.observable();
		childNursingRemain: KnockoutObservable<string> = ko.observable();
		nursingRemain: KnockoutObservable<string> = ko.observable();
		// yearRemain
		// yearHourRemain
		// childNursingRemain
		// childNursingHourRemain
		// nursingRemain
		// nirsingHourRemain


        created(params: AppInitParam) {
            const vm = this;

			let empLst: Array<string> = [],
				dateLst: Array<string> = [];
			if (!_.isEmpty(params)) {
				if (!_.isEmpty(params.employeeIds)) {
					empLst = params.employeeIds;
				}
				if (!_.isEmpty(params.baseDate)) {
					let paramDate = moment(params.baseDate).format('YYYY/MM/DD');
					dateLst = [paramDate];
					vm.application().appDate(paramDate);
					vm.application().opAppStartDate(paramDate);
                    vm.application().opAppEndDate(paramDate);
				}
				if (params.isAgentMode) {
					vm.isAgentMode(params.isAgentMode);
				}
            }
            
            vm.$blockui("show");
			vm.loadData(empLst, dateLst, vm.appType())
				.then((loadDataFlag: any) => {
					if (loadDataFlag) {
						let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput);
						return vm.$ajax(API.startNew, appDispInfoStartupOutput);
					}
				}).then((successData: any) => {
					if (successData) {
						console.log(successData);

						vm.data = successData;
						let hdAppSetInput: any[] = vm.data.hdAppSet.dispNames;
						if (hdAppSetInput && hdAppSetInput.length > 0) {
							vm.hdAppSet(hdAppSetInput);
						}
					}
				}).fail((failData: any) => {
					console.log(failData);
				}).always(() => {
                    vm.$blockui("hide");
                });
        }

        mounted() {
			const vm = this;
			
			vm.selectedWorkTypeCD.subscribe(() => {
				if (_.isNil(vm.selectedWorkTypeCD()) || _.isEmpty(vm.workTypeLst())) {
					return;
				}
			});

			vm.timeRequired(nts.uk.time.format.byId("Clock_Short_HM", vm.requiredVacationTime()));
			
			// check selected item
            vm.selectedType.subscribe(() => {
				console.log(this.selectedType())
				let appDates = [];
				if (_.isNil(vm.application().opAppStartDate())) {
					appDates.push(vm.application().opAppStartDate());
				}
				if (_.isNil(vm.application().opAppEndDate()) && vm.application().opAppStartDate() !== vm.application().opAppEndDate()) {
					appDates.push(vm.application().opAppEndDate());
				}

                let command = {
					companyID: __viewContext.user.companyId,
					appDates: appDates,
					startInfo: vm.data,
					holidayAppType: vm.selectedType()
				};

                vm.$blockui("show");
                vm.$ajax(API.getAllAppForLeave, command).done((result) => {
					vm.fetchData(result);
                }).fail((fail) => {

                }).always(() => {
                    vm.$blockui("hide");
                })
			});
			
			// disply condtion for item A10_3
			vm.isDispMourn = ko.computed(() => {
				if (vm.specAbsenceDispInfo()) {
					if (vm.specAbsenceDispInfo().specHdForEventFlag && vm.specAbsenceDispInfo().specHdEvent.maxNumberDay === 2 && vm.specAbsenceDispInfo().specHdEvent.makeInvitation === 1) {
						return true;
					}
				}

				return false;
			});

			// change appDate for app type
			vm.application.subscribe(app => {
				if (app) {
					let startDate = app.opAppStartDate();
					let endDate = app.opAppEndDate();
					let checkFormat = vm.validateAppDate(startDate, endDate);

					if (checkFormat) {
						vm.changeAppDate();
					}
				}
			});
		}
		
		fetchData(data: any) {
			const vm = this;

			vm.data = data;
			vm.selectedWorkTypeCD(data.selectedWorkTypeCD);

			let workTypeLstOutput = data.workTypeLst;
			// vm.workTypeLst(_.forEach(workTypeLstOutput, item => item.name = item.workTypeCode + ' ' + item.name));
			vm.workTypeLst(_.map(workTypeLstOutput, item => new WorkType({workTypeCode: item.workTypeCode, name: item.workTypeCode + ' ' + item.name})));
			// item => item.name = item.workTypeCode + ' ' + item.name)

			vm.appDispInfoStartupOutput(data.appDispInfoStartupOutput);
			vm.specAbsenceDispInfo(data.specAbsenceDispInfo);

			if (vm.specAbsenceDispInfo()) {
				vm.dateSpecHdRelationLst(vm.specAbsenceDispInfo().dateSpecHdRelationLst);
				
				if (vm.dateSpecHdRelationLst() && vm.dateSpecHdRelationLst().length > 0) {
					vm.selectedDateSpec(vm.dateSpecHdRelationLst()[0].relationCD);
				}

				vm.maxNumberOfDay(vm.$i18n("KAF006_44").concat("\n"));

				if (vm.isDispMourn() && vm.isCheckMourn()) {
					let param = vm.specAbsenceDispInfo().maxDay + vm.specAbsenceDispInfo().dayOfRela
					vm.maxNumberOfDay(vm.maxNumberOfDay().concat(vm.$i18n("KAF006_46", param.toString())));
				} else {
					let param = vm.specAbsenceDispInfo().maxDay;
					vm.maxNumberOfDay(vm.maxNumberOfDay().concat(vm.$i18n("KAF006_46", param.toString())));
				}
			}

			if (data.requiredVacationTime) {
				vm.requiredVacationTime(data.requiredVacationTime);
			}

			if (data.remainVacationInfo) {
				vm.yearRemain(data.remainVacationInfo.yearRemain);
				vm.subHdRemain(data.remainVacationInfo.subHdRemain);
				vm.subVacaRemain(data.remainVacationInfo.subVacaRemain);
				vm.remainingHours(data.remainVacationInfo.remainingHours);
				vm.fetchRemainTime(data.remainVacationInfo);
			}

		}

        register() {
			const vm = this;
			vm.$blockui("show");
			// validate chung KAF000
			vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
			.then((isValid) => {
				if (isValid) {
					// validate riêng cho màn hình
					return vm.$validate('.inputTime');
				}
			}).then((result) => {
				// check trước khi đăng kí
				if(result) {
					return vm.$ajax('at', API.checkBeforeRegister, ["Msg_26"]);
				}
			}).then((result) => {
				if (result) {
					// xử lý confirmMsg
					return vm.handleConfirmMessage(result);
				}
			}).then((result) => {
				if(result) {
					// đăng kí 
					return vm.$ajax('at', API.register, ["Msg_15"]).then(() => {
						return vm.$dialog.info({ messageId: "Msg_15"}).then(() => {
							return true;
						});	
					});
				}
			}).then((result) => {
				if(result) {
					// gửi mail sau khi đăng kí
					// return vm.$ajax('at', API.sendMailAfterRegisterSample);
					return true;
				}	
			}).fail((failData) => {
				// xử lý lỗi nghiệp vụ riêng
				vm.handleErrorCustom(failData).then((result: any) => {
					if(result) {
						// xử lý lỗi nghiệp vụ chung
						vm.handleErrorCommon(failData);
					}
				});
			}).always(() => {
				vm.$blockui("hide");	
			});
		}
		
		handleErrorCustom(failData: any): any {
			const vm = this;
			if(failData.messageId == "Msg_26") {
				vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds })
				.then(() => {
					vm.$jump("com", "/view/ccg/008/a/index.xhtml");	
				});
				return $.Deferred().resolve(false);		
			}
			return $.Deferred().resolve(true);
		}

		handleConfirmMessage(listMes: any): any {
			const vm = this;
			if(_.isEmpty(listMes)) {
				return $.Deferred().resolve(true);
			}
			let msg = listMes[0];

			return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
			.then((value) => {
				if (value === 'yes') {
					return vm.handleConfirmMessage(_.drop(listMes));
				} else {
					return $.Deferred().resolve(false);
				}
			});
		}

        annualHolidayRefer() {

        }

        openHolidays() {

		}

		validateAppDate(start: string, end: string) {
			let startDate = moment(start);
			let endDate = moment(end);
			if (startDate.isValid() && endDate.isValid()) {
				return true;
			}
			return false;
		}

		changeAppDate() {
			const vm = this;

			let startDate = vm.application().opAppStartDate(),
				endDate = vm.application().opAppEndDate();
			let appDates = []
			appDates.push(startDate, endDate);
			let command = {
				companyID: __viewContext.user.companyId,
				appDates: appDates,
				startInfo: vm.data,
				holidayAppType: vm.selectedType(),
				appWithDate: vm.appDispInfoStartupOutput().appDispInfoWithDateOutput
			};

			vm.$validate([
				'#kaf000-a-component4 .nts-input'
			]).then((valid: boolean) => {
				if (valid) {
					return vm.$blockui("show").then(() => vm.$ajax(API.changeAppDate, command));
				}
			}).done((res: any) => {
				if (res) {
					vm.fetchData(res);
				}
			}).fail(err => {
				console.log(err)
				if (err.messageId === "Msg_43") {
					vm.$dialog.error(err).then(() => { vm.$jump("com", "/view/ccg/008/a/index.xhtml"); });

				} else {
					vm.$dialog.error(err);
				}
			}).always(() => vm.$blockui("hide"));
		}

		fetchRemainTime(remainVacationInfo: any) {
			const vm = this;

			// set over60HHourRemain
			if (remainVacationInfo.over60HHourRemain) {
				vm.over60HHourRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.over60HHourRemain));
			}

			// set subVacaHourRemain
			if (remainVacationInfo.subVacaHourRemain) {
				vm.subVacaHourRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.subVacaHourRemain));
			}

			// set yearRemain
			if (remainVacationInfo.yearRemain && remainVacationInfo.yearRemain > 0) {
				if (remainVacationInfo.yearHourRemain && remainVacationInfo.yearHourRemain > 0) {
					vm.timeYearLeave(remainVacationInfo.yearRemain.toString().concat("日と").concat(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.yearHourRemain)));
				} else {
					vm.timeYearLeave(remainVacationInfo.yearRemain.toString().concat("日"));
				}
			} else {
				vm.timeYearLeave(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.yearHourRemain));
			}

			// set childNursingRemain
			if (remainVacationInfo.childNursingRemain && remainVacationInfo.childNursingRemain > 0) {
				if (remainVacationInfo.childNursingHourRemain && remainVacationInfo.childNursingHourRemain > 0) {
					vm.childNursingRemain(remainVacationInfo.childNursingRemain.toString().concat("日と").concat(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.childNursingHourRemain)));
				} else {
					vm.childNursingRemain(remainVacationInfo.childNursingRemain.toString().concat("日"));
				}
			} else {
				vm.childNursingRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.childNursingHourRemain));
			}

			// set nursingRemain
			if (remainVacationInfo.nursingRemain && remainVacationInfo.nursingRemain > 0) {
				if (remainVacationInfo.nursingRemain && remainVacationInfo.nirsingHourRemain > 0) {
					vm.nursingRemain(remainVacationInfo.nursingRemain.toString().concat("日と").concat(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.nirsingHourRemain)));
				} else {
					vm.nursingRemain(remainVacationInfo.nursingRemain.toString().concat("日"));
				}
			} else {
				vm.nursingRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.nirsingHourRemain));
			}
		}
		
		public openKDL035() {
			let params = {};
			Kaf006ShrViewModel.openDialogKDL035(params);
		}

		public openKDL036() {
			let params = {};
			Kaf006ShrViewModel.openDialogKDL036(params);
		}
    }

    const API = {
		startNew: 'at/request/application/appforleave/getAppForLeaveStart',
		getAllAppForLeave: 'at/request/application/appforleave/getAllAppForLeave',
		changeAppDate: 'at/request/application/appforleave/findChangeAppdate',
		checkBeforeRegister: 'at/request/application/appforleave/checkBeforeRegister',
		register: 'at/request/application/appforleave/insert'
    }
}