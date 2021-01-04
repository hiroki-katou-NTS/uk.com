/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;
import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
import ApplicationDto = nts.uk.at.view.kaf006.shr.viewmodel.ApplicationDto;
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
		selectedWorkTimeCD: KnockoutObservable<string> = ko.observable();
		dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedDateSpec: KnockoutObservable<any> = ko.observable();
		relationshipReason: KnockoutObservable<string> = ko.observable();
		maxNumberOfDay: KnockoutObservable<any> = ko.observable();
		specAbsenceDispInfo: KnockoutObservable<any> = ko.observable();
		isDispMourn: any = ko.observable(false);
		isCheckMourn: any = ko.observable(false);
		requiredVacationTime: KnockoutObservable<number> = ko.observable(1200);
		timeRequired: KnockoutObservable<string> = ko.observable();
		leaveComDayOffManas: KnockoutObservableArray<any> = ko.observableArray([]);
		payoutSubofHDManagements: KnockoutObservable<any> = ko.observableArray([]);
		workTypeBefore: KnockoutObservable<any> = ko.observable();
		workTypeAfter: KnockoutObservable<any> = ko.observable();
		isFromOther: boolean = false;

		yearRemain: KnockoutObservable<number> = ko.observable();
		subHdRemain: KnockoutObservable<number> = ko.observable();
		subVacaRemain: KnockoutObservable<number> = ko.observable();
		remainingHours: KnockoutObservable<number> = ko.observable();

		over60HHourRemain: KnockoutObservable<string> = ko.observable();
		subVacaHourRemain: KnockoutObservable<string> = ko.observable();
		timeYearLeave: KnockoutObservable<string> = ko.observable();
		childNursingRemain: KnockoutObservable<string> = ko.observable();
		nursingRemain: KnockoutObservable<string> = ko.observable();
		isChangeWorkHour: KnockoutObservable<boolean> = ko.observable(false);
		startTime1: KnockoutObservable<number> = ko.observable();
        endTime1: KnockoutObservable<number> = ko.observable();
        startTime2: KnockoutObservable<number> = ko.observable();
		endTime2: KnockoutObservable<number> = ko.observable();
		
        // 60H超休
        over60H: KnockoutObservable<number> = ko.observable();
        // 時間代休
        timeOff: KnockoutObservable<number> = ko.observable();
        // 時間年休
        annualTime: KnockoutObservable<number> = ko.observable();
        // 子の看護
        childNursing: KnockoutObservable<number> = ko.observable();
        // 介護時間
		nursing: KnockoutObservable<number> = ko.observable();
		
		// Condition
		condition10: KnockoutObservable<boolean> = ko.observable(true);
		condition1_21: KnockoutObservable<boolean> = ko.observable(true);
		condition11: KnockoutObservable<boolean> = ko.observable(true);
		condition30: KnockoutObservable<boolean> = ko.observable(true);
		condition12: KnockoutObservable<boolean> = ko.observable(true);

        created(params: AppInitParam) {
            const vm = this;
			if(!_.isNil(__viewContext.transferred.value)) {
				vm.isFromOther = true;
			}
			sessionStorage.removeItem('nts.uk.request.STORAGE_KEY_TRANSFER_DATA');
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
			
			// Load data common
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
			
			// check selected item
            vm.selectedType.subscribe(() => {
				// if ($("#work-type-combobox").ntsError("hasError")) {
				// 	$("#work-type-combobox").ntsError("clear");
				// }

				vm.$errors("clear");
				
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
			
			// Subscribe workType value after change
			vm.selectedWorkTypeCD.subscribe(() => {
				console.log(vm.selectedWorkTypeCD());
				if (_.isNil(vm.selectedWorkTypeCD()) || _.isEmpty(vm.workTypeLst())) {
					return;
				}

				// return;
				let commandCheckTyingManage = {
					wtBefore: vm.workTypeBefore(),
					wtAfter: vm.workTypeAfter(),
					leaveComDayOffMana: [],
					payoutSubofHDManagements: []
				};

				// Check vacation tying manage
				// 休暇紐付管理をチェックする
				vm.$blockui("show");
				vm.$ajax(API.checkVacationTyingManage, commandCheckTyingManage)
					.done((success) => {
						if (success) {

						}
					}).fail((error) => {
						if (error) {

						}
					}).always(() => {
						vm.$blockui("hide");
					});

				let dates = [];
				if (vm.application().opAppStartDate()) {
					dates.push(vm.application().opAppStartDate());
				}
				if (vm.application().opAppEndDate() && (vm.application().opAppEndDate() !== vm.application().opAppStartDate())) {
					dates.push(vm.application().opAppEndDate());
				}

				let commandChangeWorkType = {
					appDates: dates,
					startInfo: vm.data,
					holidayAppType: vm.selectedType(),
					workTypeCd: vm.selectedWorkTypeCD()
				};
				// Process change workType
				// 勤務種類変更時処理
				vm.$blockui("show");
				vm.$ajax(API.changeWorkType, commandChangeWorkType)
					.done((success) => {
						if (success) {

						}
					}).fail((error) => {
						if (error) {

						}
					}).always(() => {
						vm.$blockui("hide");
					});
			});

			vm.timeRequired(nts.uk.time.format.byId("Clock_Short_HM", vm.requiredVacationTime()));
			
			
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
			let workTypeLstOutput = data.workTypeLst;
			// vm.workTypeLst(_.forEach(workTypeLstOutput, item => item.name = item.workTypeCode + ' ' + item.name));
			vm.workTypeLst(_.map(workTypeLstOutput, item => new WorkType({workTypeCode: item.workTypeCode, name: item.workTypeCode + ' ' + item.name})));
			// item => item.name = item.workTypeCode + ' ' + item.name)
			
			// Get value workType before change workType List
			let workTypesBefore = _.filter(vm.data.workTypeLst, {'workTypeCode': vm.selectedWorkTypeCD()});
			vm.workTypeBefore(workTypesBefore.length > 0 ? workTypesBefore[0] : null);

			vm.data = data;
			vm.checkCondition10(data);
			vm.checkCondition11(data);
			vm.checkCondition12(data);
			vm.checkCondition30(data);
			let workTypesAfter = _.filter(vm.data.workTypeLst, {'workTypeCode': data.selectedWorkTypeCD});
			vm.workTypeAfter(workTypesAfter.length > 0 ? workTypesAfter[0] : null);

			vm.selectedWorkTypeCD(data.selectedWorkTypeCD);


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

		// Register data
        register() {
			const vm = this;

			// Update appAbsenceStartInfo
			vm.updateAppAbsenceStartInfo();

			// Create data Vacation Request/ 休暇申請
			// vm.createDataVacationApp();
			let appDates = [];
			if (vm.application().opAppStartDate()) {
				appDates.push(vm.application().opAppStartDate());
			};
			if (vm.application().opAppEndDate()) {
				appDates.push(vm.application().opAppEndDate());
			};

			let holidayAppDates = [];

			let application: ApplicationDto = new ApplicationDto(
				null, 
				null, 
				ko.toJS(vm.application().prePostAtr), 
				vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].sid,
				ko.toJS(vm.application().appType), 
				ko.toJS(vm.application().appDate),
				 null, 
				 null, 
				 null, 
				 null, 
				 ko.toJS(vm.application().opReversionReason), 
				 ko.toJS(vm.application().appDate), 
				 ko.toJS(vm.application().appDate), 
				 ko.toJS(vm.application().opAppReason), 
				 ko.toJS(vm.application().opAppStandardReasonCD));

			let commandCheckRegister = {
				appAbsenceStartInfoDto: vm.data,
				applyForLeave: this.createDataVacationApp(),
				agentAtr: false,
				application: application
			};

			let appTypeSettingLst = vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting;
			let qr = _.filter(appTypeSettingLst, { 'appType': vm.application().appType });


			let commandRegister = {
				applyForLeave: this.createDataVacationApp(),
				appDates: appDates,
				leaveComDayOffMana: vm.leaveComDayOffManas(),
				payoutSubofHDManagements: vm.payoutSubofHDManagements(),
				mailServerSet: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.mailServerSet,
				application: application,
				approvalRoot: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.opListApprovalPhaseState,
				apptypeSetting: qr.length > 0 ? qr[0] : null
			};

			vm.$blockui("show");
			// validate chung KAF000
			vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
			.then((isValid) => {
				if (isValid) {
					// validate riêng cho màn hình
					return true;
				}
			}).then((result) => {
				// check trước khi đăng kí
				if(result) {
					return vm.$ajax('at', API.checkBeforeRegister, commandCheckRegister);
				}
			}).then((result) => {
				if (result) {
					holidayAppDates = result.holidayDateLst;
					commandRegister.appDates = holidayAppDates;
					// xử lý confirmMsg
					return vm.handleConfirmMessage(result.confirmMsgLst);
				}
			}).then((result) => {
				if(result) {
					// đăng kí 
					return vm.$ajax('at', API.register, commandRegister);
				}
			}).done((result) => {
				if (result) {
					return vm.$dialog.info({ messageId: "Msg_15"}).then(() => {
						return true;
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

		/**
		 * Create Data for for Vacation Application
		 */
		createDataVacationApp(): any {
			const vm = this;
			
			// application common

			// A4_2
			// Holiday Type
			let holidayAppType = vm.selectedType();

			// A5_2
			// List of workType
			let workType = vm.selectedWorkTypeCD();

			// A6_5
			// worktTime
			let workTime = vm.selectedWorkTimeCD();

			// A6_1
			let workChangeUse = vm.isChangeWorkHour();

			let startTime1 = vm.startTime1();
			let endTime1 = vm.endTime1();
			let startTime2 = vm.startTime2();
			let endTime2 = vm.endTime2();

			let workingHours = [];

			if (startTime1 && endTime1) {
				workingHours.push({
					workNo: 1,
					timeZone: {
						startTime: startTime1,
						endTime: endTime1
					}
				});
			}
			if (startTime2 && endTime2) {
				workingHours.push({
					workNo: 2,
					timeZone: {
						startTime: startTime2,
						endTime: endTime2
					}
				});
			}

			let timeDegestion = {};
			if (vm.selectedType() === 6) {
				timeDegestion = {
					overtime60H: vm.over60H(),
					nursingTime: vm.nursing(),
					childTime: vm.childNursing(),
					timeOff: vm.timeOff(),
					timeSpecialVacation: 0,
					timeAnualLeave: vm.annualTime(),
					specialVacationFrameNO: null
				};
			}

			let applyForSpeLeaveOptional = {};
			if (vm.selectedType() === 3) {
				applyForSpeLeaveOptional = {
					mournerFlag: vm.isCheckMourn,
					applyForSpeLeaveOptional: vm.selectedDateSpec(),
					relationshipReason: vm.relationshipReason()
				};
			}

			if (vm.selectedType() === 1) {
				if (vm.leaveComDayOffManas().length > 0) {

				}
				if (vm.payoutSubofHDManagements().length > 0) {

				}
			}

			let appAbsence = {
				reflectFreeTimeApp: {
					workingHours: workingHours,
					timeDegestion: timeDegestion,
					workInfo: {
						workType: workType,
						workTime: workTime
					},
					workChangeUse: workChangeUse ? 1 : 0
				},
				vacationInfo: {
					holidayApplicationType: holidayAppType,
					info: {
						datePeriod: {
							startDate: vm.application().opAppStartDate(),
							endDate: vm.application().opAppEndDate()
						},
						applyForSpeLeave: applyForSpeLeaveOptional
					}
				}
			};

			return appAbsence;
		}


		/**
		 * Update data for AppAbsenceStartInfo
		 */
		updateAppAbsenceStartInfo() {
			const vm = this;

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
			if (failData.messageId === "Msg_478") {
				
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

		checkCondition10(data: any) {
			const vm = this;
			if (vm.data && vm.data.remainVacationInfo && (
				vm.data.remainVacationInfo.substituteLeaveManagement.substituteLeaveManagement === 1 ||
				vm.data.remainVacationInfo.overtime60hManagement.overrest60HManagement === 1 ||
				vm.data.remainVacationInfo.annualLeaveManagement.annualLeaveManageDistinct === 1 ||
				vm.data.remainVacationInfo.accumulatedRestManagement.accumulatedManage === 1
			)) {
				vm.condition10(true);
				return true;
			}
			vm.condition10(false);
			return false
		}

		checkCondition11(data: any) {
			const vm = this;
			if (vm.data && vm.data.workHoursDisp) {
				vm.condition11(true);
				return true;
			}
			vm.condition11(false);
			return false;
		}

		checkCondition30(data: any) {
			const vm = this;
			if (vm.data && vm.data.vacationApplicationReflect && vm.data.vacationApplicationReflect.workAttendanceReflect.reflectAttendance === 1) {
				vm.condition30(true);
				return true;
			}
			vm.condition30(false);
			return false;
		}

		checkCondition12(data: any) {
			const vm = this;
			if (vm.data && vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles) {
				vm.condition12(true);
				return true;
			}
			vm.condition12(false);
			return false;
		}
		
		public openKDL035() {
			const vm = this;

			let workType = _.filter(vm.data.workTypeLst, {'workTypeCode': vm.selectedWorkTypeCD()});

			let params: any = {
				// 社員ID
				employeeId: __viewContext.user.employeeId,

				// 申請期間
				period: {startDate: vm.application().opAppStartDate(), endDate: vm.application().opAppEndDate()},

				// 日数単位（1.0 / 0.5）
				daysUnit: workType[0].workAtr === 0 ? 1.0 : 0.5,

				// 対象選択区分（自動 / 申請 / 手動
				targetSelectionAtr: 1,

				// List<表示する実績内容>
				actualContentDisplayList: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,

				// List<振出振休紐付け管理>
				managementData: ko.toJS(vm.leaveComDayOffManas)
			};
			Kaf006ShrViewModel.openDialogKDL035(params);
		}

		public openKDL036() {
			const vm = this;

			let workType = _.filter(vm.data.workTypeLst, {'workTypeCode': vm.selectedWorkTypeCD()});

			let params: any = {
				// 社員ID
				employeeId: __viewContext.user.employeeId,

				// 申請期間
				period: {startDate: vm.application().opAppStartDate(), endDate: vm.application().opAppEndDate()},

				// 日数単位（1.0 / 0.5）
				daysUnit: workType[0].workAtr === 0 ? 1.0 : 0.5,

				// 対象選択区分（自動 / 申請 / 手動
				targetSelectionAtr: 1,

				// List<表示する実績内容>
				actualContentDisplayList: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,

				// List<振出振休紐付け管理>
				managementData: ko.toJS(vm.leaveComDayOffManas)
			};

			Kaf006ShrViewModel.openDialogKDL036(params);
		}


    }

    const API = {
		startNew: 'at/request/application/appforleave/getAppForLeaveStart',
		getAllAppForLeave: 'at/request/application/appforleave/getAllAppForLeave',
		changeAppDate: 'at/request/application/appforleave/findChangeAppdate',
		checkBeforeRegister: 'at/request/application/appforleave/checkBeforeRegister',
		register: 'at/request/application/appforleave/insert',
		checkVacationTyingManage: 'at/request/application/appforleave/checkVacationTyingManage',
		changeWorkType: 'at/request/application/appforleave/findChangeWorkType'
    }
}