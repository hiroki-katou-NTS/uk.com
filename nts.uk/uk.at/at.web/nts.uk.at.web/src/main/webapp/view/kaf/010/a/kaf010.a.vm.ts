/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


module nts.uk.at.view.kaf010.a.viewmodel {
	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;
	import OvertimeWork = nts.uk.at.view.kaf010.shr.header.viewmodel.OvertimeWork;
	import WorkInfo = nts.uk.at.view.kaf010.shr.work_info.viewmodel.WorkInfo;
	import Work = nts.uk.at.view.kaf010.shr.work_info.viewmodel.Work;
	import WorkHours = nts.uk.at.view.kaf010.shr.work_info.viewmodel.WorkHours;
	import OverTime = nts.uk.at.view.kaf010.shr.time.viewmodel.OverTime;
	import RestTime = nts.uk.at.view.kaf010.shr.time.viewmodel.RestTime;
	import HolidayTime = nts.uk.at.view.kaf010.shr.time.viewmodel.HolidayTime;
	import formatTime = nts.uk.time.format.byId;
	

	@bean()
	export class Kaf010ViewModel extends Kaf000AViewModel {

		appType: KnockoutObservable<number> = ko.observable(AppType.HOLIDAY_WORK_APPLICATION);
		isAgentMode : KnockoutObservable<boolean> = ko.observable(false);
		application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
		isSendMail: KnockoutObservable<Boolean>;
		overTimeWork: KnockoutObservableArray<OvertimeWork> = ko.observableArray([]);
		workInfo: KnockoutObservable<WorkInfo> = ko.observable(null);
		overTime: KnockoutObservableArray<OverTime> = ko.observableArray([]);
		restTime: KnockoutObservableArray<RestTime> = ko.observableArray([]);
		holidayTime: KnockoutObservableArray<HolidayTime> = ko.observableArray([]);
		comboDivergenceReason: KnockoutObservableArray<ComboDivergenceReason> = ko.observableArray([]);
		dataSource: AppHdWorkDispInfo;
		managementMultipleWorkCyclescheck: KnockoutObservable<boolean> = ko.observable(false);
		inputReflectDivergenceCheck : KnockoutObservable<boolean> = ko.observable(false);
		selectReflectDivergenceCheck: KnockoutObservable<boolean> = ko.observable(false);
		workTimeCheckBoxVisible: KnockoutObservable<boolean> = ko.observable(false);
		referenceButton: KnockoutObservable<boolean> = ko.observable(false);
		inputEnable: KnockoutObservable<boolean> = ko.observable(false);
		nightOvertimeReflectAtrCheck: KnockoutObservable<boolean> = ko.observable(true);
		restTimeTableVisible: KnockoutObservable<boolean> = ko.observable(false);
		overTimeTableVisible: KnockoutObservable<boolean> = ko.observable(false);
		restTimeTableVisible2: KnockoutObservable<boolean> = ko.observable(false);
		isGoWorkAtr: KnockoutObservable<boolean> = ko.observable(false);
		isBackHomeAtr: KnockoutObservable<boolean> = ko.observable(false);
		selectedDivergenceReasonCode: KnockoutObservable<string> = ko.observable();
		mode: KnockoutObservable<number> = ko.observable(MODE.NORMAL);
		employeeIdLst: Array<string>;

		constructor() {
			super();
			const vm = this;
			
		}

		created(params: AppInitParam) {
			const vm = this;

			vm.createRestTime();
			vm.setMode(params);
			// vm.createOverTime();

			let empList: Array<string> = [],
				dateList: Array<string> = [];
			if (!_.isEmpty(params)) {
				if (!_.isEmpty(params.employeeIds)) {
					empList = params.employeeIds;
				}
				if (!_.isEmpty(params.baseDate)) {
					let paramDate = moment(params.baseDate).format('YYYY/MM/DD');
					dateList = [paramDate];
					vm.application().appDate(paramDate);
					vm.application().opAppStartDate(paramDate);
                    vm.application().opAppEndDate(paramDate);
				}
				if (params.isAgentMode) {
					vm.isAgentMode(params.isAgentMode);
				}
			}
			vm.isSendMail = ko.observable(false);

			vm.$blockui("show");
			vm.loadData(empList, dateList, vm.appType())
				.then((loadDataFlag: any) => {
					vm.application().appDate.subscribe(value => {
						console.log(value);
						if (value) {
							vm.changeDate();
						}
					});
					// ※17
					vm.application().prePostAtr.subscribe(value => {
						if(vm.mode() == MODE.MULTiPLE_AGENT){
							return;
						}
						if (value == 0) {
							console.log('trigger1')
							$('.table-time2 .nts-fixed-header-wrapper').width(224);
							if (vm.holidayTime().length > 3) {
								$('.table-time2 .nts-fixed-body-wrapper').width(208);
							} else {
								$('.table-time2 .nts-fixed-body-wrapper').width(225);
							}
							
							$('.table-time3 .nts-fixed-header-wrapper').width(224);
							if (vm.overTime().length > 6) {
								$('.table-time3 .nts-fixed-body-wrapper').width(208);
							} else {
								$('.table-time3 .nts-fixed-body-wrapper').width(225);
							}
							
						} else {
							console.log('trigger2')
							$('.table-time2 .nts-fixed-header-wrapper').width(455);
							$('.table-time2 .nts-fixed-body-wrapper').width(455);
							$('.table-time3 .nts-fixed-header-wrapper').width(455);
							$('.table-time3 .nts-fixed-body-wrapper').width(455);
						}
					});
					if (loadDataFlag) {
						let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
							command = { empList, dateList, appDispInfoStartupOutput };
						return vm.$ajax(API.startNew, command);
					}
					
				}).then((successData: any) => {
					if (successData) {
						console.log(successData, 'res');
						vm.dataSource = successData;
						vm.bindOverTimeWorks(vm.dataSource);
						vm.bindWorkInfo(vm.dataSource, true);
						vm.bindRestTime(vm.dataSource);
						vm.bindHolidayTime(vm.dataSource, 1);
						vm.bindOverTime(vm.dataSource, 1);
						vm.itemControlHandler();
						vm.setComboDivergenceReason(vm.dataSource);

						vm.workInfo().workHours1.start.subscribe((value) => {
							if (_.isNumber(value)) {
								vm.getBreakTimes();
							}
						});

						vm.workInfo().workHours1.end.subscribe((value) => {
							if (_.isNumber(value)) {
								vm.getBreakTimes();
							}
						});

						if (!_.isEmpty(params)) {
							if (!_.isEmpty(params.baseDate)) {
								
							}
						}
						
						if (vm.application().prePostAtr() == 0 || vm.mode() == MODE.MULTiPLE_AGENT) {
							$('.table-time2 .nts-fixed-header-wrapper').width(224);
							if (vm.holidayTime().length > 3) {
								$('.table-time2 .nts-fixed-body-wrapper').width(208);
							} else {
								$('.table-time2 .nts-fixed-body-wrapper').width(225);
							}
							
							$('.table-time3 .nts-fixed-header-wrapper').width(224);
							if (vm.overTime().length > 6) {
								$('.table-time3 .nts-fixed-body-wrapper').width(208);
							} else {
								$('.table-time3 .nts-fixed-body-wrapper').width(225);
							}
						} 
					}
				}).fail((failData: any) => {
					if (failData.messageId === "Msg_43") {
						vm.$dialog.error(failData).then(() => { vm.$jump("com", "/view/ccg/008/a/index.xhtml"); });

					} else {
						vm.$dialog.error(failData);
					}
				}).always(() => {vm.$blockui("hide"); $('#kaf000-a-component4-singleDate').focus();});
		}

		mounted() {
			const self = this;
		}
		// ※20	
		setMode(params: any) {
			const self = this;
			if (_.isNil(params)) {
				self.mode(MODE.NORMAL);
			} else {
				if (params.isAgentMode) {
					if (!_.isEmpty(params.employeeIds)) {
						self.employeeIdLst = params.employeeIds;
						if (params.employeeIds.length == 1) {
							self.mode(MODE.SINGLE_AGENT);
						} else {
							self.mode(MODE.MULTiPLE_AGENT);
						}
					}
				}
			}
		}

		itemControlHandler() {
			const self = this;
			console.log('star handle');
			// ※28
			self.managementMultipleWorkCyclescheck(self.dataSource.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles);
			
			// ※27
			if (self.dataSource.hdWorkOvertimeReflect.holidayWorkAppReflect.reflectDivergence == 1 && self.dataSource.useInputDivergenceReason == true) {
				self.inputReflectDivergenceCheck(true);
			} else {
				self.inputReflectDivergenceCheck(false);
			}
			// ※26
			if (self.dataSource.hdWorkOvertimeReflect.holidayWorkAppReflect.reflectDivergence == 1 && self.dataSource.useComboDivergenceReason == true) {
				self.selectReflectDivergenceCheck(true);
			} else {
				self.selectReflectDivergenceCheck(false);
			}
			// ※23
			if (self.dataSource.holidayWorkAppSet.useDirectBounceFunction == 1 && self.dataSource.holidayWorkAppSet.applicationDetailSetting.timeCalUse == 1) {
				self.workTimeCheckBoxVisible(true);
			} else {
				self.workTimeCheckBoxVisible(false);
			}
			// ※22
			self.referenceButton(self.dataSource.hdWorkDispInfoWithDateOutput.subHdManage);
			// ※21
			if (self.dataSource.holidayWorkAppSet.applicationDetailSetting.timeInputUse == 1) { // co van de
				self.inputEnable(true)
			} else {
				self.inputEnable(false)
			}
			// ※18
			if (self.dataSource.hdWorkOvertimeReflect.nightOvertimeReflectAtr == 0) {
				self.nightOvertimeReflectAtrCheck(false);
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length})`).hide();
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length-1})`).hide();
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length-2})`).hide();
				$(`#fixed-overtime-hour-table tr:nth-child(${self.overTime().length})`).hide();
			} else {
				self.nightOvertimeReflectAtrCheck(true);
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length})`).show();
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length-1})`).show();
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length-2})`).show();
				$(`#fixed-overtime-hour-table tr:nth-child(${self.overTime().length})`).show();
			}
			// ※16
			if (self.dataSource.hdWorkOvertimeReflect.holidayWorkAppReflect.reflectBreakOuting == 1) {
				self.restTimeTableVisible(true);
			} else {
				self.restTimeTableVisible(false);
			}
			// ※15
			if (!_.isNil(self.dataSource.calculationResult)) {
				if (!_.isEmpty(self.dataSource.calculationResult.applicationTime.applicationTime)) {
					self.overTimeTableVisible(true);
				}
			} else {
				self.overTimeTableVisible(false);
			}
			// ※7
			if (self.dataSource.holidayWorkAppSet.applicationDetailSetting.timeCalUse == 1) {
				self.restTimeTableVisible2(true);
			} else {
				self.restTimeTableVisible2(false);
			}
		}

		toAppHolidayWork(){
			let vm = this;
			let appHolidayWork = {} as AppHolidayWorkCmd;
			let listApplicationTime = [] as Array<OvertimeApplicationSetting>
			let listMidNightHolidayTimes = [];
			let overTimeMidNight = null;
			for (let i = 0; i < vm.holidayTime().length; i++) {
				if (vm.holidayTime()[i].type() == 1 && !_.isNil(vm.holidayTime()[i].start())) {
					let holidayTime = {} as OvertimeApplicationSetting;
					holidayTime.frameNo = vm.holidayTime()[i].frameNo();
					holidayTime.attendanceType = 1;
					holidayTime.applicationTime = vm.holidayTime()[i].start();
					listApplicationTime.push(holidayTime);
				}
				if (!_.isNil(vm.holidayTime()[i].legalClf()) && !_.isNil(vm.holidayTime()[i].start())) {
					listMidNightHolidayTimes.push({attendanceTime: vm.holidayTime()[i].start(), legalClf: vm.holidayTime()[i].legalClf()});
				}
			}
			for (let i = 0; i < vm.overTime().length; i++) {
				if (vm.overTime()[i].type() == 0 && !_.isNil(vm.overTime()[i].applicationTime())) {
					let overTime = {} as OvertimeApplicationSetting;
					overTime.frameNo = vm.overTime()[i].frameNo();
					overTime.attendanceType = 0;
					overTime.applicationTime = vm.overTime()[i].applicationTime();
					listApplicationTime.push(overTime);
				}
				if (vm.overTime()[i].type() === 100 && !_.isNil(vm.overTime()[i].applicationTime())) {
					overTimeMidNight = vm.overTime()[i].applicationTime();
				}
			}
			appHolidayWork.workInformation = {} as WorkInformationCommand;
			appHolidayWork.workInformation.workType = vm.workInfo().workType().code;
			appHolidayWork.workInformation.workTime = vm.workInfo().workTime().code;

			appHolidayWork.applicationTime = {} as ApplicationTime;
			appHolidayWork.applicationTime.applicationTime = listApplicationTime;
			appHolidayWork.applicationTime.overTimeShiftNight = {} as OverTimeShiftNight;
			appHolidayWork.applicationTime.overTimeShiftNight.midNightHolidayTimes = listMidNightHolidayTimes;
			appHolidayWork.applicationTime.overTimeShiftNight.overTimeMidNight = overTimeMidNight;

			appHolidayWork.application = ko.toJS(vm.application);

			appHolidayWork.workingTimeList = [] as Array<TimeZoneWithWorkNoCommand>;
			if(!_.isNil(vm.workInfo().workHours1.start()) && !_.isNil(vm.workInfo().workHours1.end())){
				let workingTime1 = {} as TimeZoneWithWorkNoCommand;
				workingTime1.workNo = 1;
				workingTime1.timeZone = {} as TimeZoneNewDto;
				workingTime1.timeZone.startTime = vm.workInfo().workHours1.start();
				workingTime1.timeZone.endTime = vm.workInfo().workHours1.end();
				appHolidayWork.workingTimeList.push(workingTime1);
			}
			
			if(!_.isNil(vm.workInfo().workHours2.start()) && !_.isNil(vm.workInfo().workHours2.end())){
				let workingTime2 = {} as TimeZoneWithWorkNoCommand;
				workingTime2.workNo = 2;
				workingTime2.timeZone = {} as TimeZoneNewDto;
				workingTime2.timeZone.startTime = vm.workInfo().workHours2.start();
				workingTime2.timeZone.endTime = vm.workInfo().workHours2.end();
				appHolidayWork.workingTimeList.push(workingTime2);
			}

			appHolidayWork.goWorkAtr = vm.isGoWorkAtr();
			appHolidayWork.backHomeAtr = vm.isBackHomeAtr();

			appHolidayWork.breakTimeList = [] as Array<TimeZoneWithWorkNoCommand>;
			for (let i = 0; i < vm.restTime().length && i < 10; i++) {
				if(!_.isNil(vm.restTime()[i].start()) && !_.isNil(vm.restTime()[i].end())){
					let restTime = {} as TimeZoneWithWorkNoCommand;
					restTime.workNo = i+1;
					restTime.timeZone = {} as TimeZoneNewDto;
					restTime.timeZone.startTime = vm.restTime()[i].start();
					restTime.timeZone.endTime = vm.restTime()[i].end();
					appHolidayWork.breakTimeList.push(restTime);
				}		
			}
			return appHolidayWork;
		}
		
		register() {
			const vm = this;

			vm.$blockui("show");
			let appHolidayWork = vm.toAppHolidayWork();
			
			vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
				.then(isValid => {
					if (isValid) {
						return true;
					}
				})
				.then(result => {
					// check before 
					let commandCheck = {
						require: true,
						companyId: vm.$user.companyId,
						appHdWorkDispInfo: vm.dataSource, 
						appHolidayWork: appHolidayWork,
						isProxy: false
					}
					
					if(result) {
						return vm.$ajax(API.checkBeforeRegister, commandCheck); // chua xong
					}
				}).then((result: CheckBeforeOutput) => {
					if (!_.isNil(result)) {
						// xử lý confirmMsg
						return vm.handleConfirmMessage(result.confirmMsgOutputs);
					}
				}).then((result) => {
					if (result) {
						let commandRegister = {
							companyId: vm.$user.companyId,
							appHolidayWork: appHolidayWork,
							appHdWorkDispInfo: vm.dataSource, 
							appTypeSetting : vm.dataSource.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting[0],
						};
						return vm.$ajax('at', API.register, commandRegister).then(() => {
							return vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
								return true;
							});
						});
					}
					
				})
				.done(result => {
					
				})
				.fail(err => {
					let messageId, messageParams;
					if(err.errors) {
						let errors = err.errors;
						messageId = errors[0].messageId;
					} else {
						messageId = err.messageId;
						messageParams = [err.parameterIds.join('、')];
					}
					vm.$dialog.error({ messageId: messageId, messageParams: messageParams });
				})
				.always(() => vm.$blockui("hide"));
		}

		setComboDivergenceReason(res: AppHdWorkDispInfo) {
			const self = this;
			if(res.comboDivergenceReason){
				self.comboDivergenceReason([res.comboDivergenceReason]);
			}
			let defaultReasonTypeItem = _.find(res.appDispInfoStartupOutput.appDispInfoNoDateOutput.reasonTypeItemLst, (o) => o.defaultValue);
			if(_.isUndefined(defaultReasonTypeItem)) {
				let dataLst = [{
					divergenceReasonCode: '',
					reason: self.$i18n('KAFS00_23'),
					reasonRequired: 1,
				}];
				self.comboDivergenceReason(_.concat(dataLst, self.comboDivergenceReason()));
				self.selectedDivergenceReasonCode(_.head(self.comboDivergenceReason()).divergenceReasonCode);
			} else {
				self.selectedDivergenceReasonCode(defaultReasonTypeItem.divergenceReasonCode);
			}
		}

		bindOverTimeWorks(res: AppHdWorkDispInfo) { // dummy data
			const self = this;
			const { otWorkHoursForApplication } = res;
			let overTimeWorks = [];
			{
				let item = new OvertimeWork();
				item.yearMonth = ko.observable(otWorkHoursForApplication.currentMonth);
				if (otWorkHoursForApplication.isCurrentMonth) {
					item.limitTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.legalMaxTime.threshold.erAlTime.error);
					item.actualTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.legalMaxTime.agreementTime);
				} else {
					item.limitTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.agreementTime.threshold.erAlTime.error);
					item.actualTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.agreementTime.agreementTime);
				}
				
				overTimeWorks.push(item);
			}
			{
				let item = new OvertimeWork();
				item.yearMonth = ko.observable(otWorkHoursForApplication.nextMonth);
				if (otWorkHoursForApplication.isNextMonth) {
					item.limitTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.legalMaxTime.threshold.erAlTime.error);
					item.actualTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.legalMaxTime.agreementTime);
				} else {
					item.limitTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.agreementTime.threshold.erAlTime.error);
					item.actualTime = ko.observable(otWorkHoursForApplication.currentTimeMonth.agreementTime.agreementTime);
				}
				
				overTimeWorks.push(item);
			}
			self.overTimeWork(overTimeWorks);
		}

		bindWorkInfo(res: AppHdWorkDispInfo, check?: boolean) { // dummy data 
			const self = this;
			const { hdWorkDispInfoWithDateOutput } = res;
			
			let workHours1 = {} as WorkHours;
			let workHours2 = {} as WorkHours;
			if (!ko.toJS(self.workInfo)) {
				workHours1.start = ko.observable(hdWorkDispInfoWithDateOutput.workHours.startTimeOp1);
				workHours1.end = ko.observable(hdWorkDispInfoWithDateOutput.workHours.endTimeOp1);
				workHours2.start = ko.observable(hdWorkDispInfoWithDateOutput.workHours.startTimeOp2);
				workHours2.end = ko.observable(hdWorkDispInfoWithDateOutput.workHours.endTimeOp2);
			} else {
				workHours1 = self.workInfo().workHours1;
				workHours2 = self.workInfo().workHours2;
				workHours1.start(hdWorkDispInfoWithDateOutput.workHours.startTimeOp1);
				workHours1.end(hdWorkDispInfoWithDateOutput.workHours.endTimeOp1);
				workHours2.start(hdWorkDispInfoWithDateOutput.workHours.startTimeOp2);
				workHours2.end(hdWorkDispInfoWithDateOutput.workHours.endTimeOp2);
			}
			
			let workInfo = new WorkInfo();
			if (check) {
				workInfo.workType({code: hdWorkDispInfoWithDateOutput.initWorkType, name: hdWorkDispInfoWithDateOutput.initWorkTypeName});
				workInfo.workTime({code: hdWorkDispInfoWithDateOutput.initWorkTime, name: hdWorkDispInfoWithDateOutput.initWorkTimeName});
			} else {
				workInfo = self.workInfo();
			}
			workInfo.workHours1 = workHours1;
			workInfo.workHours2 = workHours2;
			self.workInfo(workInfo);
		}

		bindRestTime(res: AppHdWorkDispInfo) {
			const self = this;
			const { hdWorkDispInfoWithDateOutput } = res;
			let restTimeArray = self.restTime() as Array<RestTime>;
			_.forEach(restTimeArray, (item: RestTime) => {
				item.start(null);
				item.end(null);
			})
			if (!_.isNil(hdWorkDispInfoWithDateOutput)) {
				let breakTime = hdWorkDispInfoWithDateOutput.breakTimeZoneSettingList;
				if (!_.isNil(breakTime)) {
					if (!_.isEmpty(breakTime.timeZones)) {
						_.forEach(breakTime.timeZones, (item: TimeZone, index) => {
							if (Number(index) < 10) {
								let restItem = restTimeArray[index] as RestTime;
								restItem.start(item.start);
								restItem.end(item.end);
							}
						})
					}
				}
			}
			self.restTime(_.clone(restTimeArray));
		}

		bindHolidayTime(res: AppHdWorkDispInfo, mode?: number) {
			const self = this;
			let holidayTimeArray = [] as Array<HolidayTime>;
			console.log(res.workdayoffFrameList.length, 'length');

			for (let i = 0; i < res.workdayoffFrameList.length; i++) {
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(res.workdayoffFrameList[i].workdayoffFrNo);
				item.frameName = ko.observable(res.workdayoffFrameList[i].workdayoffFrName);
				item.start = ko.observable(res.calculationResult && res.calculationResult.calculatedFlag == 0 ? 0 : null);
				item.preApp = ko.observable(null);
				item.actualTime = ko.observable(null);
				item.type = ko.observable(1);
				item.legalClf = ko.observable(null);
				holidayTimeArray.push(item);
			}

			{	// A6_27
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(holidayTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_342'));
				item.start = ko.observable(res.calculationResult && res.calculationResult.calculatedFlag == 0 ? 0 : null);
				item.preApp = ko.observable(null);
				item.actualTime = ko.observable(null);
				item.legalClf = ko.observable(0);
				item.type = ko.observable(6);
				holidayTimeArray.push(item);
			}

			{
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(holidayTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_343'));
				item.start = ko.observable(res.calculationResult && res.calculationResult.calculatedFlag == 0 ? 0 : null);
				item.preApp = ko.observable(null);
				item.actualTime = ko.observable(null);
				item.legalClf = ko.observable(1);
				item.type = ko.observable(7);
				holidayTimeArray.push(item);
			}

			{
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(holidayTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_344'));
				item.start = ko.observable(res.calculationResult && res.calculationResult.calculatedFlag == 0 ? 0 : null);
				item.preApp = ko.observable(null);
				item.actualTime = ko.observable(null);
				item.legalClf = ko.observable(2);
				item.type = ko.observable(8);
				holidayTimeArray.push(item);
			}

			if (mode == 1) {
				let calculationResultOp = res.calculationResult;
					// A6_8
				if (!_.isEmpty(calculationResultOp)) {
						let applicationTime = calculationResultOp.applicationTime.applicationTime;
						if (!_.isEmpty(applicationTime)) {	
							_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
								holidayTimeArray
									.filter(holidayTime => holidayTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.BREAKTIME)
									.map(holidayTime => holidayTime.start(item.applicationTime));
							})	
						}
						// A6_28 , A6_35, A6_40
						let appRoot = calculationResultOp.applicationTime;
						if (!_.isNil(appRoot.overTimeShiftNight)) {
							let midNightHolidayTimes = appRoot.overTimeShiftNight.midNightHolidayTimes;
							if (!_.isEmpty(midNightHolidayTimes)) {
								_.forEach(midNightHolidayTimes, (item: HolidayMidNightTime) => {
									if (item.legalClf == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {
										holidayTimeArray
											.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_BREAK_TIME)
											.map(holidayTime => holidayTime.start(item.attendanceTime));
									} else if (item.legalClf == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {
										holidayTimeArray
											.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_EXORBITANT_HOLIDAY)
											.map(holidayTime => holidayTime.start(item.attendanceTime));
									} else if (item.legalClf == StaturoryAtrOfHolidayWork.PublicHolidayWork) {
										holidayTimeArray
											.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_HOLIDAY_HOLIDAY)
											.map(holidayTime => holidayTime.start(item.attendanceTime));
									}
								});
							}
						}
				}
				// A6_9
				// 申請表示情報．申請表示情報(基準日関係あり)．表示する事前申請内容．残業申請．申請時間．申請時間．申請時間
				let opPreAppContentDisplayLst = res.appDispInfoStartupOutput.appDispInfoWithDateOutput.opPreAppContentDispDtoLst;
				if (!_.isEmpty(opPreAppContentDisplayLst)) {
					let appHolidayWork = opPreAppContentDisplayLst[0].appHolidayWork;
					if (appHolidayWork) {
						let applicationTime = appHolidayWork.applicationTime.applicationTime;
						if (!_.isEmpty(applicationTime)) {
							_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
								holidayTimeArray
									.filter(holidayTime => holidayTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.BREAKTIME)
									.map(holidayTime => holidayTime.preApp(item.applicationTime));
							})
						}
					}
					// A6_29 A6_36 A6_41
					let appRoot = opPreAppContentDisplayLst[0].appHolidayWork.applicationTime;
						if (!_.isNil(appRoot.overTimeShiftNight)) {
							let midNightHolidayTimes = appRoot.overTimeShiftNight.midNightHolidayTimes;
							if (!_.isEmpty(midNightHolidayTimes)) {
								_.forEach(midNightHolidayTimes, (item: HolidayMidNightTime) => {
									if (item.legalClf == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {
										holidayTimeArray
											.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_BREAK_TIME)
											.map(holidayTime => holidayTime.preApp(item.attendanceTime));
									} else if (item.legalClf == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {
										holidayTimeArray
											.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_EXORBITANT_HOLIDAY)
											.map(holidayTime => holidayTime.preApp(item.attendanceTime));
									} else if (item.legalClf == StaturoryAtrOfHolidayWork.PublicHolidayWork) {
										holidayTimeArray
											.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_HOLIDAY_HOLIDAY)
											.map(holidayTime => holidayTime.preApp(item.attendanceTime));
									}
								});
							}
						}
					
					}
				// A6_11
				if (!_.isNil(res.hdWorkDispInfoWithDateOutput)) {
					if (!_.isEmpty(res.hdWorkDispInfoWithDateOutput.actualApplicationTime)) {
						let actualApplicationTime = res.hdWorkDispInfoWithDateOutput.actualApplicationTime;
						if (actualApplicationTime) {
							let applicationTime = actualApplicationTime.applicationTime[0];
							if (!_.isEmpty(applicationTime)) {
								_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
									holidayTimeArray
										.filter(holidayTime => holidayTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.BREAKTIME)
										.map(holidayTime => holidayTime.actualTime(item.applicationTime));
								})
							}
						}
						// A6_30 A6_37 A6_42
						let appRoot = res.hdWorkDispInfoWithDateOutput.actualApplicationTime;
							if (!_.isNil(appRoot.overTimeShiftNight)) {
								let midNightHolidayTimes = appRoot.overTimeShiftNight.midNightHolidayTimes;
								if (!_.isEmpty(midNightHolidayTimes)) {
									_.forEach(midNightHolidayTimes, (item: HolidayMidNightTime) => {
										if (item.legalClf == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {
											holidayTimeArray
												.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_BREAK_TIME)
												.map(holidayTime => holidayTime.actualTime(item.attendanceTime));
										} else if (item.legalClf == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {
											holidayTimeArray
												.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_EXORBITANT_HOLIDAY)
												.map(holidayTime => holidayTime.actualTime(item.attendanceTime));
										} else if (item.legalClf == StaturoryAtrOfHolidayWork.PublicHolidayWork) {
											holidayTimeArray
												.filter(holidayTime => holidayTime.type() == AttendanceType.MIDDLE_HOLIDAY_HOLIDAY)
												.map(holidayTime => holidayTime.actualTime(item.attendanceTime));
										}
									});
								}
							}
						}
				}
			}

			self.holidayTime(holidayTimeArray);
			self.setColorForHolidayTime(self.dataSource.calculationResult && self.dataSource.calculationResult.calculatedFlag == 0, self.dataSource);
		}

		setColorForHolidayTime(isCalculation: Boolean, dataSource: AppHdWorkDispInfo) {
			const self = this;
			if (!isCalculation || _.isNil(dataSource.calculationResult)) {
				return;
			}
			let holidayTimes = self.holidayTime() as Array<HolidayTime>;
			let overStateOutput = dataSource.calculationResult.actualOvertimeStatus;
			
			_.forEach(holidayTimes, (item: HolidayTime) => {
				let backgroundColor = '';
				if (item.type() == AttendanceType.BREAKTIME) {
					if (!_.isNil(overStateOutput)) {
						// ・計算値：「残業申請の表示情報．計算結果」を確認する
						if (!_.isNil(dataSource.calculationResult.applicationTime)) {
							let applicationTime = dataSource.calculationResult.applicationTime.applicationTime;
							if (!_.isEmpty(applicationTime)) {
								let result = _.find(applicationTime, (i: OvertimeApplicationSetting) => {
									return i.frameNo == Number(item.frameNo) && i.attendanceType == AttendanceType.BREAKTIME;
								});
								if (!_.isNil(result)) {
									if (result.applicationTime > 0) {
										backgroundColor = BACKGROUND_COLOR.bgC1;									
									}
								}
							}
						}
						// ・事前申請超過：「残業申請の表示情報．計算結果．事前申請・実績の超過状態」を確認する
						if (!_.isNil(overStateOutput.advanceExcess)) {
							let excessStateDetail = overStateOutput.advanceExcess.excessStateDetail;
							if (!_.isEmpty(excessStateDetail)) {
								let result = _.find(excessStateDetail, (i: ExcessStateDetail) => {
									return i.frame == Number(item.frameNo) && i.type == AttendanceType.BREAKTIME;
								});
								if (!_.isNil(result)) {
									if (result.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC4;
									}
								}
							}
							
						}
						// ・実績申請超過：「残業申請の表示情報．計算結果．事前申請・実績の超過状態」を確認する
						if (!_.isNil(overStateOutput.achivementExcess)) {
							let excessStateDetail = overStateOutput.advanceExcess.excessStateDetail;
							if (!_.isEmpty(excessStateDetail)) {
								let result = _.find(excessStateDetail, (i: ExcessStateDetail) => {
									return i.frame == Number(item.frameNo) && i.type == AttendanceType.BREAKTIME;
								});
								if (!_.isNil(result)) {
									if (result.excessState == ExcessState.EXCESS_ERROR) {
										backgroundColor = BACKGROUND_COLOR.bgC2;
									} else if (result.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC3;
									}
									
								}
							}
							
						}
						
					}
				} else if (item.type() == AttendanceType.MIDDLE_BREAK_TIME) {
					// 計算結果．申請時間．就業時間外深夜時間．休出深夜時間．時間 > 0
					// 法定区分 = 法定内休出
					if (!_.isEmpty(dataSource.calculationResult.applicationTime)) {
						let overTimeShiftNight = dataSource.calculationResult.applicationTime.overTimeShiftNight;
						if (!_.isNil(overTimeShiftNight)) {
							if (!_.isEmpty(overTimeShiftNight.midNightHolidayTimes)) {
								let findResult = _.find(overTimeShiftNight.midNightHolidayTimes, (i: HolidayMidNightTime) => i.legalClf == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.attendanceTime > 0) {
										backgroundColor = BACKGROUND_COLOR.bgC1;										
									}
								}
							}
						}
					}
					
					// 事前申請・実績の超過状態．事前超過．休出深夜時間．法定区分 = 法定内休出
					// 事前申請・実績の超過状態．事前超過．休出深夜時間．超過状態 = 超過アラーム
					if (!_.isNil(overStateOutput.advanceExcess)) {
						let excessStateMidnight = overStateOutput.advanceExcess.excessStateMidnight;
						if (!_.isEmpty(excessStateMidnight)) {
							let findResult = _.find(excessStateMidnight, (i: ExcessStateMidnight) => i.legalCfl == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC4;										
									}
								}
						}
					}
					if (!_.isNil(overStateOutput.achivementExcess)) {
						let excessStateMidnight = overStateOutput.achivementExcess.excessStateMidnight;
						if (!_.isEmpty(excessStateMidnight)) {
							let findResult = _.find(excessStateMidnight, (i: ExcessStateMidnight) => i.legalCfl == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC3;										
									} else if (findResult.excessState == ExcessState.EXCESS_ERROR) {
										backgroundColor = BACKGROUND_COLOR.bgC2;	
									}
								}
						}
					}
				} else if (item.type() == AttendanceType.MIDDLE_EXORBITANT_HOLIDAY) {
					// 計算結果．申請時間．就業時間外深夜時間．休出深夜時間．時間 > 0
					// 法定区分 = 法定内休出
					if (!_.isEmpty(dataSource.calculationResult.applicationTime)) {
						let overTimeShiftNight = dataSource.calculationResult.applicationTime.overTimeShiftNight;
						if (!_.isNil(overTimeShiftNight)) {
							if (!_.isEmpty(overTimeShiftNight.midNightHolidayTimes)) {
								let findResult = _.find(overTimeShiftNight.midNightHolidayTimes, (i: HolidayMidNightTime) => i.legalClf == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.attendanceTime > 0) {
										backgroundColor = BACKGROUND_COLOR.bgC1;										
									}
								}
							}
						}
					}
					
					// 事前申請・実績の超過状態．事前超過．休出深夜時間．法定区分 = 法定内休出
					// 事前申請・実績の超過状態．事前超過．休出深夜時間．超過状態 = 超過アラーム
					if (!_.isNil(overStateOutput.advanceExcess)) {
						let excessStateMidnight = overStateOutput.advanceExcess.excessStateMidnight;
						if (!_.isEmpty(excessStateMidnight)) {
							let findResult = _.find(excessStateMidnight, (i: ExcessStateMidnight) => i.legalCfl == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC4;										
									}
								}
						}
					}
					
					if (!_.isNil(overStateOutput.achivementExcess)) {
						let excessStateMidnight = overStateOutput.achivementExcess.excessStateMidnight;
						if (!_.isEmpty(excessStateMidnight)) {
							let findResult = _.find(excessStateMidnight, (i: ExcessStateMidnight) => i.legalCfl == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC3;										
									} else if (findResult.excessState == ExcessState.EXCESS_ERROR) {
										backgroundColor = BACKGROUND_COLOR.bgC2;	
									}
								}
						}
					}
					
				} else if (item.type() == AttendanceType.MIDDLE_HOLIDAY_HOLIDAY) {
					// 計算結果．申請時間．就業時間外深夜時間．休出深夜時間．時間 > 0
					// 法定区分 = 法定内休出
					if (!_.isEmpty(dataSource.calculationResult.applicationTime)) {
						let overTimeShiftNight = dataSource.calculationResult.applicationTime.overTimeShiftNight;
						if (!_.isNil(overTimeShiftNight)) {
							if (!_.isEmpty(overTimeShiftNight.midNightHolidayTimes)) {
								let findResult = _.find(overTimeShiftNight.midNightHolidayTimes, (i: HolidayMidNightTime) => i.legalClf == StaturoryAtrOfHolidayWork.PublicHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.attendanceTime > 0) {
										backgroundColor = BACKGROUND_COLOR.bgC1;										
									}
								}
							}
						}
					}
					// 事前申請・実績の超過状態．事前超過．休出深夜時間．法定区分 = 法定内休出
					// 事前申請・実績の超過状態．事前超過．休出深夜時間．超過状態 = 超過アラーム
					if (!_.isNil(overStateOutput.advanceExcess)) {
						let excessStateMidnight = overStateOutput.advanceExcess.excessStateMidnight;
						if (!_.isEmpty(excessStateMidnight)) {
							let findResult = _.find(excessStateMidnight, (i: ExcessStateMidnight) => i.legalCfl == StaturoryAtrOfHolidayWork.PublicHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC4;										
									}
								}
						}
					}
					
					if (!_.isNil(overStateOutput.achivementExcess)) {
						let excessStateMidnight = overStateOutput.achivementExcess.excessStateMidnight;
						if (!_.isEmpty(excessStateMidnight)) {
							let findResult = _.find(excessStateMidnight, (i: ExcessStateMidnight) => i.legalCfl == StaturoryAtrOfHolidayWork.PublicHolidayWork);
								if (!_.isNil(findResult)) {
									if (findResult.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC3;										
									} else if (findResult.excessState == ExcessState.EXCESS_ERROR) {
										backgroundColor = BACKGROUND_COLOR.bgC2;	
									}
								}
						}
					}
					
				}
				item.backgroundColor = ko.observable(backgroundColor);
			});
		}

		bindOverTime(res: AppHdWorkDispInfo, mode?: number) {
			const self = this;
			let overTimeArray = [] as Array<OverTime>;

			for (let i = 0; i < res.overtimeFrameList.length; i++) {
				let item = {} as OverTime;
				item.frameNo = ko.observable(res.overtimeFrameList[i].overtimeWorkFrNo);
				item.frameName = ko.observable(res.overtimeFrameList[i].overtimeWorkFrName);
				item.applicationTime = ko.observable(res.calculationResult && res.calculationResult.calculatedFlag == 0 ? 0 : null);
				item.preTime = ko.observable(null);
				item.actualTime = ko.observable(null);
				item.type = ko.observable(0);
				overTimeArray.push(item);
			}

			{
				let item = {} as OverTime;
				item.frameNo = ko.observable(overTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_61'));
				item.applicationTime = ko.observable(res.calculationResult && res.calculationResult.calculatedFlag == 0 ? 0 : null);
				item.preTime = ko.observable(null);
				item.actualTime = ko.observable(null);
				item.type = ko.observable(null);
				item.type = ko.observable(100);
				overTimeArray.push(item);
			}
			// A7_8
			let calculationResultOp = res.calculationResult;
			if (!_.isNil(calculationResultOp)) {
				if (!_.isEmpty(calculationResultOp.applicationTime)) {
					let applicationTime = calculationResultOp.applicationTime.applicationTime;
					if (!_.isEmpty(applicationTime)) {
						_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
							overTimeArray
									.filter(overTime => overTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.NORMALOVERTIME)
									.map(overTime => overTime.applicationTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? item.applicationTime : null));
						});
					}
				}
			}

			if (mode == 1) {
					
				let calculationResultOp = res.calculationResult;
				if (!_.isNil(calculationResultOp)) {
					if (!_.isEmpty(calculationResultOp.applicationTime)) {
						// A7_8
						let applicationTime = calculationResultOp.applicationTime.applicationTime;
						if (!_.isEmpty(applicationTime)) {
							_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
								overTimeArray
									.filter(overTime => overTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.NORMALOVERTIME)
									.map(overTime => overTime.applicationTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? item.applicationTime : null));
							});
						}
						// A7_13
						// 計算結果．申請時間．就業時間外深夜時間 
						let overTimeShiftNight = res.calculationResult.applicationTime.overTimeShiftNight;
						if (!_.isNil(overTimeShiftNight)) {
							overTimeArray
								.filter(overTime => overTime.type() == AttendanceType.MIDNIGHT_OUTSIDE)
								.map(overTime => overTime.applicationTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? overTimeShiftNight.overTimeMidNight : null));													
						}
					}
				}

				// A7_9
				let opPreAppContentDisplayLst = res.appDispInfoStartupOutput.appDispInfoWithDateOutput.opPreAppContentDispDtoLst;
				if (!_.isEmpty(opPreAppContentDisplayLst)) {
					let apOptional = opPreAppContentDisplayLst[0].apOptional;
					if (apOptional) {
						let applicationTime = apOptional.applicationTime as ApplicationTime;
						if (!_.isEmpty(applicationTime)) {
							_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
								overTimeArray
									.filter(overTime => overTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.NORMALOVERTIME)
									.map(overTime => overTime.preTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? item.applicationTime : null));
							})
							// A7_14
							let overTimeShiftNight = applicationTime.overTimeShiftNight;
							if (!_.isNil(overTimeShiftNight)) {
								overTimeArray
									.filter(overTime => overTime.type() == AttendanceType.MIDNIGHT_OUTSIDE)
									.map(overTime => overTime.preTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? overTimeShiftNight.overTimeMidNight : null));													
							}
						}
					}
				}
				// A7_11
				if (!_.isNil(res.hdWorkDispInfoWithDateOutput)) {
					if (!_.isNil(res.hdWorkDispInfoWithDateOutput.actualApplicationTime)) {
						let applicationTimeRoot = res.hdWorkDispInfoWithDateOutput.actualApplicationTime;
						let applicationTime = res.hdWorkDispInfoWithDateOutput.actualApplicationTime.applicationTime;
						if (!_.isEmpty(applicationTime)) {
							_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
								overTimeArray
									.filter(overTime => overTime.frameNo() == item.frameNo && item.attendanceType == AttendanceType.NORMALOVERTIME)
									.map(overTime => overTime.actualTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? item.applicationTime : null));
							})
						}
						if (!_.isNil(applicationTimeRoot)) {
							// A7_15
							// 申請日に関係する情報．実績の申請時間．就業時間外深夜時間．残業深夜時間
							let overTimeShiftNight = applicationTimeRoot.overTimeShiftNight;
							if (!_.isNil(overTimeShiftNight)) {
								overTimeArray
									.filter(overTime => overTime.type() == AttendanceType.MIDNIGHT_OUTSIDE)
									.map(overTime => overTime.actualTime(calculationResultOp && calculationResultOp.calculatedFlag == 0 ? overTimeShiftNight.overTimeMidNight : null));	
							}				
						}
					}
				}	
			}

			self.overTime(overTimeArray);
			self.setColorForOverTime(self.dataSource.calculationResult && self.dataSource.calculationResult.calculatedFlag == 0, self.dataSource);
		}

		setColorForOverTime(isCalculation: Boolean, dataSource: AppHdWorkDispInfo) {
			const self = this;
			if (!isCalculation || _.isNil(dataSource.calculationResult)) {
				return;
			}
			let overTimes = self.overTime() as Array<OverTime>;
			let overStateOutput = dataSource.calculationResult.actualOvertimeStatus;
			
			_.forEach(overTimes, (item: OverTime) => {
				let backgroundColor = '';
				if (item.type() == AttendanceType.NORMALOVERTIME) {
					if (!_.isNil(overStateOutput)) {
						// ・計算値：「残業申請の表示情報．計算結果」を確認する
						if (!_.isNil(dataSource.calculationResult.applicationTime)) {
							let applicationTime = dataSource.calculationResult.applicationTime.applicationTime;
							if (!_.isEmpty(applicationTime)) {
								let result = _.find(applicationTime, (i: OvertimeApplicationSetting) => {
									return i.frameNo == Number(item.frameNo) && i.attendanceType == AttendanceType.NORMALOVERTIME;
								});
								if (!_.isNil(result)) {
									if (result.applicationTime > 0) {
										backgroundColor = BACKGROUND_COLOR.bgC1;									
									}
								}
							}
						}
						// ・事前申請超過：「残業申請の表示情報．計算結果．事前申請・実績の超過状態」を確認する
						if (!_.isNil(overStateOutput.advanceExcess)) {
							let excessStateDetail = overStateOutput.advanceExcess.excessStateDetail;
							if (!_.isEmpty(excessStateDetail)) {
								let result = _.find(excessStateDetail, (i: ExcessStateDetail) => {
									return i.frame == Number(item.frameNo) && i.type == AttendanceType.NORMALOVERTIME;
								});
								if (!_.isNil(result)) {
									if (result.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC4;
									}
								}
							}
						}
						// ・実績申請超過：「残業申請の表示情報．計算結果．事前申請・実績の超過状態」を確認する
						if (!_.isNil(overStateOutput.achivementExcess)) {
							let excessStateDetail = overStateOutput.advanceExcess.excessStateDetail;
							if (!_.isEmpty(excessStateDetail)) {
								let result = _.find(excessStateDetail, (i: ExcessStateDetail) => {
									return i.frame == Number(item.frameNo) && i.type == AttendanceType.NORMALOVERTIME;
								});
								if (!_.isNil(result)) {
									if (result.excessState == ExcessState.EXCESS_ERROR) {
										backgroundColor = BACKGROUND_COLOR.bgC2;
									} else if (result.excessState == ExcessState.EXCESS_ALARM) {
										backgroundColor = BACKGROUND_COLOR.bgC3;
									}
								}
							}
						}
					}
				} else if (item.type() == AttendanceType.MIDNIGHT_OUTSIDE) {
					
					// 事前申請・実績の超過状態．事前超過．残業深夜の超過状態 = 超過アラーム
					if (!_.isNil(overStateOutput)) {
						
						if (!_.isNil(dataSource.calculationResult.applicationTime)) {
							let applicationTime = dataSource.calculationResult.applicationTime;
							if (!_.isEmpty(applicationTime)) {
								if (!_.isNil(applicationTime.overTimeShiftNight)) {
									if (applicationTime.overTimeShiftNight.overTimeMidNight > 0) {
										backgroundColor = BACKGROUND_COLOR.bgC1;
									}
								}
							}
						}
						if (!_.isNil(overStateOutput.advanceExcess)) {
							if (overStateOutput.advanceExcess.overTimeLate == ExcessState.EXCESS_ALARM) {								
								backgroundColor = BACKGROUND_COLOR.bgC4;
							}							
						}
						if (!_.isNil(overStateOutput.achivementExcess)) {
							// 事前申請・実績の超過状態．実績超過．残業深夜の超過状態 = 超過アラーム
							if (overStateOutput.achivementExcess.overTimeLate == ExcessState.EXCESS_ALARM) {								
								backgroundColor = BACKGROUND_COLOR.bgC3;
							}
							// 事前申請・実績の超過状態．実績超過．残業深夜の超過状態 = 超過エラー
							if (overStateOutput.achivementExcess.overTimeLate == ExcessState.EXCESS_ERROR) {								
								backgroundColor = BACKGROUND_COLOR.bgC2;
							}
						}
					}
				}
				item.backgroundColor = ko.observable(backgroundColor);

			});
		}

		createRestTime() {
			const self = this;
			let restTimeArray = [];
			for (let i = 1; i <= 10; i++) {
				let item = {} as RestTime;
				item.frameNo = i.toString();
				item.start = ko.observable(null);
				item.end = ko.observable(null);
				restTimeArray.push(item);
			}
			self.restTime(restTimeArray);
		}

		openDialogKdl005() {
			const self = this;
			nts.uk.ui.windows.setShared( 'KDL005_DATA', {
				employeeIds: self.employeeIdLst,
				baseDate: self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.baseDate.replaceAll('/', ""),
            }, true);

			if(self.mode()==MODE.MULTiPLE_AGENT){
				nts.uk.ui.windows.sub.modal('/view/kdl/005/a/multi.xhtml').onClosed( function(): any {})
			}else{
				nts.uk.ui.windows.sub.modal('/view/kdl/005/a/single.xhtml').onClosed( function(): any {})
			}

		}

		openDialogKdl003() {
			const self = this;
			nts.uk.ui.windows.setShared( 'parentCodes', {
                workTypeCodes: _.map( _.uniqBy( self.dataSource.hdWorkDispInfoWithDateOutput.workTypeList, e => e.workTypeCode ), (item: any) => item.workTypeCode ),
                selectedWorkTypeCode: self.workInfo().workType().code,
                workTimeCodes: _.map( self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode ),
                selectedWorkTimeCode: self.workInfo().workTime().code
            }, true);

			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed( function(): any {
                //view all code of selected item 
                let childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
					let workType = {} as Work;
					workType.code = childData.selectedWorkTypeCode;
					workType.name = childData.selectedWorkTypeName;
					self.workInfo().workType(workType);
					let workTime = {} as Work;
                    workTime.code = childData.selectedWorkTimeCode;
					workTime.name = childData.selectedWorkTimeName;
					self.workInfo().workTime(workTime);

					let dateList = self.application().appDate().trim() ? [self.application().appDate().trim()] : [];
					let command = {
						companyId: self.$user.companyId,
						dateList,
						workTypeCode: workType.code,
						workTimeCode: workTime.code,
						startTime: 0,
						endTime: 0,
						appHdWorkDispInfoDto: self.dataSource
					};

					self.$blockui('show');
					self.$ajax(API.selectWorkInfo, command)
						.done((res: AppHdWorkDispInfo) => {
						
							self.dataSource.appDispInfoStartupOutput = res.appDispInfoStartupOutput;
							self.dataSource.calculationResult = res.calculationResult;
							self.dataSource.dispFlexTime = res.dispFlexTime;
							self.dataSource.hdWorkDispInfoWithDateOutput = res.hdWorkDispInfoWithDateOutput;
							self.dataSource.holidayWorkAppSet = res.holidayWorkAppSet;
							self.dataSource.otWorkHoursForApplication = res.otWorkHoursForApplication;
							self.dataSource.overtimeFrameList = res.overtimeFrameList;
							self.dataSource.useComboDivergenceReason = res.useComboDivergenceReason;
							self.dataSource.useInputDivergenceReason = res.useInputDivergenceReason;
							self.dataSource.workdayoffFrameList = res.workdayoffFrameList;

							self.bindOverTimeWorks(self.dataSource);
							self.bindWorkInfo(self.dataSource);
							self.bindRestTime(self.dataSource);
							self.bindHolidayTime(self.dataSource, 1);
							self.bindOverTime(self.dataSource, 1);

							
						})
						.fail((res) =>{
							console.log(res, 'fail');
						})
						.always(() =>{
							self.$blockui('hide');
						})

                }
            })
		}

		getBreakTimes() {
			const self = this;
			let command = {
				companyId: self.$user.companyId,
				dateList: [self.application().appDate()],
				workTypeCode: self.workInfo().workType().code,
				workTimeCode: self.workInfo().workTime().code,
				startTime: self.workInfo().workHours1.start(),
				endTime: self.workInfo().workHours1.end(),
				appHdWorkDispInfoDto: self.dataSource
			};
			self.$blockui('show');
			self.$ajax(API.changeWorkHours, command)
				.done((res: AppHdWorkDispInfo) =>{
					
					self.dataSource.appDispInfoStartupOutput = res.appDispInfoStartupOutput;
					self.dataSource.calculationResult = res.calculationResult;
					self.dataSource.dispFlexTime = res.dispFlexTime;
					self.dataSource.hdWorkDispInfoWithDateOutput = res.hdWorkDispInfoWithDateOutput;
					self.dataSource.holidayWorkAppSet = res.holidayWorkAppSet;
					self.dataSource.otWorkHoursForApplication = res.otWorkHoursForApplication;
					self.dataSource.overtimeFrameList = res.overtimeFrameList;
					self.dataSource.useComboDivergenceReason = res.useComboDivergenceReason;
					self.dataSource.useInputDivergenceReason = res.useInputDivergenceReason;
					self.dataSource.workdayoffFrameList = res.workdayoffFrameList;

					self.bindRestTime(self.dataSource);
				})
				.fail(() =>{})
				.always(() => self.$blockui('hide'));
		}

		

		changeDate() {
			const self = this;
			let param = {} as ParamHolidayWorkChangeDate;
			param.companyId = self.$user.companyId;
			param.dateList = [self.application().appDate()];
			param.applicationType = AppType.HOLIDAY_WORK_APPLICATION;
			param.appHdWorkDispInfoDto = ko.toJS(self.dataSource);
			self.$blockui('show');
			self.$ajax(API.changeAppDate, param)
				.done((res: AppHdWorkDispInfo) => { 
					self.dataSource = res;
					self.bindOverTimeWorks(self.dataSource);
					self.bindWorkInfo(self.dataSource, true);
					self.bindRestTime(self.dataSource);
					self.bindHolidayTime(self.dataSource, 1);
					self.bindOverTime(self.dataSource, 1);
				})
				.fail(() => {})
				.always(() => self.$blockui('hide'));
		}

		calculate() {
			const self = this;
			
			let command = {} as ParamCalculationHolidayWork;
			let workContent = {} as WorkContent;
			let workInfo = self.workInfo() as WorkInfo;
			workContent.workTypeCode = workInfo.workType().code as string;
			workContent.workTimeCode = workInfo.workTime().code as string;
			let breakTimeArray = [] as Array<BreakTime>;
			let restTime = self.restTime() as Array<RestTime>;
			let timeZoneArray = [] as Array<TimeZone>;

			command.companyId = self.$user.companyId;
			command.employeeId = self.dataSource.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst[0].sid;
			command.date = self.application().appDate();
			command.prePostAtr = self.application().prePostAtr();
			command.overtimeLeaveAppCommonSet = self.dataSource.holidayWorkAppSet.overtimeLeaveAppCommonSet;
			if (!_.isNil(self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.opPreAppContentDispDtoLst)) {
				command.preApplicationTime = self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.opPreAppContentDispDtoLst[0].appHolidayWork && 
					self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.opPreAppContentDispDtoLst[0].appHolidayWork.applicationTime;
			} 
			command.actualApplicationTime = self.dataSource.hdWorkDispInfoWithDateOutput.actualApplicationTime;

			for (let i = 1; i <= 2; i++) {
				let timeZone = {} as TimeZone;
				if (!(_.isNil(workInfo.workHours1.start()) || _.isNil(workInfo.workHours1.end()))) {
					timeZone.frameNo = i;
					timeZone.start = workInfo.workHours1.start();
					timeZone.end = workInfo.workHours1.end();
					timeZoneArray.push(timeZone);
				}
			}
			restTime.forEach(item => {
				if (!(_.isNil(ko.toJS(item.start)) || _.isNil(ko.toJS(item.end)))) {
					let breakTime = {} as BreakTime;
					breakTime.breakFrameNo = Number(item.frameNo);
					breakTime.startTime = ko.toJS(item.start);
					breakTime.endTime = ko.toJS(item.end);
					breakTime.breakTime = 0;
					breakTimeArray.push(breakTime);
				}
			})
			workContent.breakTimes = breakTimeArray;
			workContent.timeZones = timeZoneArray;
			command.workContent = workContent;
			console.log(command, 'calCMD');
			self.$ajax(API.calculate, command)
				.done((res: HolidayWorkCalculationResult) => {
					if (res) {
						self.dataSource.calculationResult = res;
						self.bindOverTime(self.dataSource, 1);
						self.bindHolidayTime(self.dataSource, 1);
						// let appTimeList = res.applicationTime.applicationTime;
						// let holidayTimeArray = self.holidayTime();
						// let overTimeArray = self.overTime();
						// console.log(holidayTimeArray, 'HLDARR');
						// console.log(self.overTime(), "OVTARR");
						// console.log(res, 'calRes');
						// appTimeList.forEach((appTime: OvertimeApplicationSetting) => {
						// 	holidayTimeArray.forEach((holidayTime: HolidayTime) => {
						// 		// A6_8
						// 		if (appTime.frameNo === holidayTime.frameNo() && appTime.attendanceType === 1) {
						// 			holidayTime.start(appTime.applicationTime);
						// 		}
						// 	})
						// 	overTimeArray.forEach((overTime: OverTime) => {
						// 		// A7_8
						// 		if (appTime.frameNo === overTime.frameNo() && appTime.attendanceType === 0) {
						// 			overTime.applicationTime(appTime.applicationTime);
						// 		} 
						// 	})
						// })
						// //A7_13
						// let length = overTimeArray.length;
						// overTimeArray[length - 1].applicationTime(res.applicationTime.overTimeShiftNight.overTimeMidNight);
						// //A6_28
						// for (let item of res.applicationTime.overTimeShiftNight.midNightHolidayTimes) {
						// 	switch (item.legalClf) {
						// 		case 0:
						// 			holidayTimeArray[length-3].start(item.attendanceTime);
						// 			break;
						// 		case 1:
						// 			holidayTimeArray[length-2].start(item.attendanceTime);
						// 			break;
						// 		case 2: 
						// 			holidayTimeArray[length-1].start(item.attendanceTime);
						// 			break;
						// 	}
							
						// } 
						// self.holidayTime(holidayTimeArray);
						// self.overTime(overTimeArray);
					}
				 })
				 .fail((res) => { 
					 console.log('calculate fail');
				 })
				 .always(() => {
					 self.$blockui("hide");
				 });
		}
		
		getFormatTime(number: number) {
			if (_.isNil(number)) return '';
			return (formatTime("Time_Short_HM", number));
		}

		handleConfirmMessage(listMes: any): any {
			const vm = this;
			if (_.isEmpty(listMes)) {
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
	}

	const API = {
		startNew: "at/request/application/holidaywork/startNew",
		changeAppDate: "at/request/application/holidaywork/changeAppDate",
		calculate: "at/request/application/holidaywork/calculate",
		selectWorkInfo: "at/request/application/holidaywork/selectWork",
		changeWorkHours:  "at/request/application/holidaywork/changeWorkHours",
		checkBeforeRegister: "at/request/application/holidaywork/checkBeforeRegister",
		register: "at/request/application/holidaywork/register"
	}
	interface AppHdWorkDispInfo {
		dispFlexTime: boolean;
		useInputDivergenceReason: boolean;
		useComboDivergenceReason: boolean;
		workdayoffFrameList: Array<WorkdayoffFrame>;
		otWorkHoursForApplication: AgreeOverTimeOutput;
		hdWorkDispInfoWithDateOutput: HdWorkDispInfoWithDateOutput;
		calculationResult: CalculationResult;
		appDispInfoStartupOutput: any;
		overtimeFrameList: Array<OvertimeWorkFrame>;
		holidayWorkAppSet: any;
		comboDivergenceReason: ComboDivergenceReason;
		hdWorkOvertimeReflect: any;
	}
	interface OvertimeWorkFrame {
		overtimeWorkFrName: string;
		overtimeWorkFrNo: number;
	}
	interface CalculationResult {
		actualOvertimeStatus: any;
		calculatedFlag: number;
		applicationTime: ApplicationTime;
	}
	interface ApplicationTime {
		applicationTime: Array<OvertimeApplicationSetting>; //  申請時間
		flexOverTime: number; // フレックス超過時間
		overTimeShiftNight: OverTimeShiftNight; // 就業時間外深夜時間
		anyItem: Array<AnyItemValue>; // 任意項目
		reasonDissociation: Array<any>; // 乖離理由
	}
	interface AnyItemValue {
		itemNo: number;
		times: number;
		amount: number;
		time: number
	}
	interface OverTimeShiftNight {
		midNightHolidayTimes: Array<any>;
		midNightOutSide: number;
		overTimeMidNight: number;
	}
	interface OvertimeApplicationSetting {
		frameNo: number; 
		attendanceType: number;
		applicationTime: number
	}
	interface HdWorkDispInfoWithDateOutput {
		initWorkType: string;
		initWorkTypeName: string;
		initWorkTime: string;
		initWorkTimeName: string;
		workHours: WorkHoursDTO;
		breakTimeZoneSettingList: BreakTimeZoneSetting;
		actualApplicationTime: ApplicationTime;
		workTypeList: Array<WorkType>;
		overtimeStatus: OvertimeStatusCommand;
		subHdManage: boolean;
	}
	interface OvertimeStatusCommand {
		isPreApplicationOvertime: boolean;
		attendanceType: number;
		isActualOvertime: boolean;
		frameNo: number;
		isInputCalculationDiff: boolean;
	}
	interface WorkType {
		workTypeCode: string;
		name: string;
	}
	interface BreakTimeZoneSetting {
		timeZones?: Array<TimeZone>;
	}
	interface TimeZone {
		frameNo: number;
		start: number;
		end: number;
	}
	interface WorkHoursDTO {
		startTimeOp1: number;
		startTimeOp2: number;
		endTimeOp1: number;
		endTimeOp2: number;
	}
	interface WorkdayoffFrame {
		workdayoffFrNo: number;
		workdayoffFrName: string;
	}
	interface AgreeOverTimeOutput {
		isCurrentMonth: boolean;
		currentTimeMonth: any;
		currentMonth: string
		isNextMonth: boolean;
		nextTimeMonth: any;
		nextMonth: string;
	}
	interface AgreeOverTime {
		detailCurrentMonth: AgreementTime;
		detailNextMonth: AgreementTime;
		currentMonth: string;
		nextMonth: string;
	}
	interface AgreementTime {
		employeeId: string;
		confirmed: AgreeTimeOfMonth;
		afterAppReflect: AgreeTimeOfMonth;
		errorMessage: string;
	}
	interface AgreeTimeOfMonth {
		agreementTime: number;
		limitErrorTime: number;
		limitAlarmTime: number;
		exceptionLimitErrorTime: number;
		exceptionLimitAlarmTime: number;
		status: number;
	}
	interface ParamHolidayWorkChangeDate {
		companyId: string;
		dateList: Array<string>;
		applicationType: number;
		appHdWorkDispInfoDto: AppHdWorkDispInfo;
	}
	interface ParamCalculationHolidayWork {
		companyId: string;
		employeeId: string;
		date: string;
		prePostAtr: number;
		overtimeLeaveAppCommonSet: OvertimeLeaveAppCommonSetCommand;
		preApplicationTime: ApplicationTime;
		actualApplicationTime: ApplicationTime;
		workContent: WorkContent;
	}
	interface OvertimeLeaveAppCommonSetCommand {
		preExcessDisplaySetting: number;
		extratimeExcessAtr: number;
		extratimeDisplayAtr: number;
		performanceExcessAtr: number;
		checkOvertimeInstructionRegister: number;
		checkDeviationRegister: number;
		overrideSet: number;
	}
	interface BreakTime {
		breakFrameNo: number;
		startTime: number;
		endTime: number;
		breakTime: number;
	}
	interface WorkContent {
		workTypeCode: string;
		workTimeCode: string;
		timeZones: Array<TimeZone>;
		breakTimes: Array<BreakTime>;
	}
	interface OverStateOutputDto {
		isExistApp: boolean;
		advanceExcess: any;
		achivementStatus: number;
		achivementExcess: any;
	}
	interface HolidayWorkCalculationResult {
		actualOvertimeStatus: OverStateOutputDto;
		applicationTime: ApplicationTime;
		calculatedFlag: number;
	}
	interface AppHolidayWorkCmd {
		workInformation: WorkInformationCommand;
		applicationTime: ApplicationTime;
		backHomeAtr: boolean;
		goWorkAtr: boolean;
		breakTimeList: Array<TimeZoneWithWorkNoCommand>;
		workingTimeList: Array<TimeZoneWithWorkNoCommand>;
		appOvertimeDetail: any;
		application: any;
	}
	interface WorkInformationCommand {
		workType: string;
		workTime: string;
	}
	interface TimeZoneWithWorkNoCommand {
		workNo: number;
		timeZone: TimeZoneNewDto;
	}
	interface TimeZoneNewDto {
		startTime: number;
		endTime: number;
	}
	interface CheckBeforeOutput {
		appOverTimeDetail: any;
		confirmMsgOutputs: Array<any>;
	}
	interface ComboDivergenceReason {
		divergenceReasonCode: string;
		reason: string;
		reasonRequired: number;
	}
	enum MODE {
		NORMAL,
		SINGLE_AGENT,
		MULTiPLE_AGENT
	}
	enum AttendanceType {
		NORMALOVERTIME,
		BREAKTIME,
		BONUSPAYTIME,
		BONUSSPECIALDAYTIME,
		MIDNIGHT,
		SHIFTNIGHT,
		MIDDLE_BREAK_TIME,
		MIDDLE_EXORBITANT_HOLIDAY,
		MIDDLE_HOLIDAY_HOLIDAY,
		FLEX_OVERTIME,
		MIDNIGHT_OUTSIDE = 100
	}
	interface HolidayMidNightTime {
		attendanceTime: number;
		legalClf: number;
	}
	enum StaturoryAtrOfHolidayWork {
		WithinPrescribedHolidayWork,
		ExcessOfStatutoryHolidayWork,
		PublicHolidayWork
	}
	const BACKGROUND_COLOR = {
		// 計算値
		bgC1: '#F69164',
		// 超過エラー
		bgC2: '#FD4D4D',
		// 超過アラーム
		bgC3: '#F6F636',
		// 事前超過
		bgC4: '#ffc0cb'
	}
	interface ExcessStateDetail {
		frame: number;
		type: number;
		excessState: number
	}
	enum ExcessState {
		NO_EXCESS,
		EXCESS_ALARM,
		EXCESS_ERROR
	}
	interface ExcessStateMidnight {
		excessState: number;
		legalCfl: number;
	}
}