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

		constructor() {
			super();
			const vm = this;
			
		}

		created(params: AppInitParam) {
			const vm = this;

			vm.createRestTime();
			
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
						console.log('trigger')
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
						vm.bindWorkInfo(vm.dataSource);
						vm.bindRestTime(vm.dataSource);
						vm.bindHolidayTime(vm.dataSource);
						vm.bindOverTime(vm.dataSource);
						vm.itemControlHandler();

						if (!_.isEmpty(params)) {
							if (!_.isEmpty(params.baseDate)) {
								
							}
						}
						
						if (vm.application().prePostAtr() == 0) {
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

		itemControlHandler() {
			const self = this;
			console.log('star handle');
			// ※28
			self.managementMultipleWorkCyclescheck(self.dataSource.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles);
			
			// ※27
			if (self.dataSource.hdWorkOvertimeReflect.holidayWorkAppReflect.reflectDivergence == 1 && self.dataSource.useInputDivergenceReason == true) {
				self.inputReflectDivergenceCheck(true);
			}
			// ※26
			if (self.dataSource.hdWorkOvertimeReflect.holidayWorkAppReflect.reflectDivergence == 1 && self.dataSource.useComboDivergenceReason == true) {
				self.selectReflectDivergenceCheck(true);
			}
			// ※23
			if (self.dataSource.holidayWorkAppSet.useDirectBounceFunction == 1 && self.dataSource.holidayWorkAppSet.applicationDetailSetting.timeCalUse == 1) {
				self.workTimeCheckBoxVisible(true);
			}
			// ※22
			self.referenceButton(self.dataSource.hdWorkDispInfoWithDateOutput.subHdManage);
			// ※21
			if (self.dataSource.holidayWorkAppSet.applicationDetailSetting.timeInputUse == 1) { // co van de
				self.inputEnable(true)
			}
			// ※18
			if (self.dataSource.hdWorkOvertimeReflect.nightOvertimeReflectAtr == 0) {
				self.nightOvertimeReflectAtrCheck(false);
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length})`).hide();
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length-1})`).hide();
				$(`#fixed-table-holiday tr:nth-child(${self.holidayTime().length-2})`).hide();
				$(`#fixed-overtime-hour-table tr:nth-child(${self.overTime().length})`).hide();
			}
			// ※16
			if (self.dataSource.hdWorkOvertimeReflect.holidayWorkAppReflect.reflectBreakOuting == 1) {
				self.restTimeTableVisible(true);
			}
			// ※15
			if (!_.isNil(self.dataSource.calculationResult)) {
				if (!_.isEmpty(self.dataSource.calculationResult.applicationTime.applicationTime)) {
					self.overTimeTableVisible(true);
				}
			}
			// ※7
			if (self.dataSource.holidayWorkAppSet.applicationDetailSetting.timeCalUse == 1) {
				self.restTimeTableVisible2(true);
			}

			

			console.log('mark1');

		}
		
		register() {
			const self = this;

			vm.$blockui("show");
			
			vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
				.then(isValid => {
					if (isValid) {
						return true;
					}
				})
				.then(result => {
					if(result) {
						return vm.$ajax(API.checkBeforeRegisterSample, ["Msg_234"]);
					}
				}).then(res => {
//					if (res) {
//						if (!_.isEmpty(res.holidayDateLst)) {
//							holidayDateLst = res.holidayDateLst;
//						}
//
//						return vm.handleConfirmMessage(_.clone(res.confirmMsgLst), command);
//					};
				}).then((result) => {
//					if(result) {
//						return vm.registerData(command);
//					};
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

		

		bindOverTimeWorks(res: AppHdWorkDispInfo) { // dummy data
			const self = this;
			const { otWorkHoursForApplication } = res;
			let overTimeWorks = [];
			{
				let item = new OvertimeWork();
				let limitTime = otWorkHoursForApplication.detailCurrentMonth.confirmed.exceptionLimitErrorTime;
				item.yearMonth = ko.observable(otWorkHoursForApplication.currentMonth);
				if (limitTime) {
					item.limitTime = ko.observable(limitTime);
				} else {
					item.limitTime = ko.observable(otWorkHoursForApplication.detailCurrentMonth.confirmed.limitErrorTime);
				}
				item.actualTime = ko.observable(otWorkHoursForApplication.detailCurrentMonth.confirmed.agreementTime);
				
				overTimeWorks.push(item);
			}
			{
				let item = new OvertimeWork();
				let limitTime = otWorkHoursForApplication.detailNextMonth.confirmed.exceptionLimitErrorTime;
				item.yearMonth = ko.observable(otWorkHoursForApplication.nextMonth)
				if (limitTime) {
					item.limitTime = ko.observable(limitTime);
				} else {
					item.limitTime = ko.observable(otWorkHoursForApplication.detailNextMonth.confirmed.limitErrorTime);
				}
				item.actualTime = ko.observable(otWorkHoursForApplication.detailNextMonth.confirmed.agreementTime);
				overTimeWorks.push(item);
			}
			
			self.overTimeWork(overTimeWorks);
		}

		bindWorkInfo(res: AppHdWorkDispInfo) { // dummy data 
			const self = this;
			const { hdWorkDispInfoWithDateOutput } = res;
			let workInfo = new WorkInfo();
			let workHours1 = {} as WorkHours;
			let workHours2 = {} as WorkHours;
			workHours1.start = ko.observable(hdWorkDispInfoWithDateOutput.workHours.startTimeOp1);
			workHours1.end = ko.observable(hdWorkDispInfoWithDateOutput.workHours.endTimeOp1);
			workHours2.start = ko.observable(hdWorkDispInfoWithDateOutput.workHours.startTimeOp2);
			workHours2.end = ko.observable(hdWorkDispInfoWithDateOutput.workHours.endTimeOp2);
			workHours1.start.subscribe((value) => {
				if (_.isNumber(value)) {
					self.getBreakTimes();
				}
			})
			workHours1.end.subscribe((value) => {
				if (_.isNumber(value)) {
					self.getBreakTimes();
				}
			})
			workInfo.workType({code: hdWorkDispInfoWithDateOutput.initWorkType, name: hdWorkDispInfoWithDateOutput.initWorkTypeName});
			workInfo.workTime({code: hdWorkDispInfoWithDateOutput.initWorkTime, name: hdWorkDispInfoWithDateOutput.initWorkTimeName});
			workInfo.workHours1 = workHours1;
			workInfo.workHours2 = workHours2;
			self.workInfo(workInfo);
		}

		bindRestTime(res: AppHdWorkDispInfo) {
			const self = this;
			const { hdWorkDispInfoWithDateOutput } = res;
			let resTimeArray = self.restTime() as Array<RestTime>;
			if (!_.isNil(hdWorkDispInfoWithDateOutput)) {
				let breakTime = hdWorkDispInfoWithDateOutput.breakTimeZoneSettingList;
				if (!_.isNil(breakTime)) {
					if (!_.isEmpty(breakTime.timeZones)) {
						_.forEach(breakTime.timeZones, (item: TimeZone, index) => {
							if (Number(index) < 10) {
								let restItem = resTimeArray[index] as RestTime;
								restItem.start(item.start);
								restItem.end(item.end);
							}
						})
					}
				}
			}
			self.restTime(_.clone(resTimeArray));
		}

		bindHolidayTime(res: AppHdWorkDispInfo) {
			const self = this;
			let holidayTimeArray = [];

			for (let i = 1; i < res.workdayoffFrameList.length; i++) {
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(res.workdayoffFrameList[i].workdayoffFrNo);
				item.frameName = ko.observable(res.workdayoffFrameList[i].workdayoffFrName);
				item.start = ko.observable(null);
				item.preApp = ko.observable(self.application().prePostAtr());
				console.log(item.preApp(), 'item preApp');
				item.actualTime = ko.observable(null);
				// item.type = ko.observable(1);
				holidayTimeArray.push(item);
			}

			{	// A6_27
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(holidayTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_342'));
				item.start = ko.observable(null);
				item.preApp = ko.observable(self.application().prePostAtr());
				item.actualTime = ko.observable(null);
				// item.type = ko.observable(1);
				holidayTimeArray.push(item);
			}

			{
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(holidayTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_343'));
				item.start = ko.observable(null);
				item.preApp = ko.observable(self.application().prePostAtr());
				item.actualTime = ko.observable(null);
				holidayTimeArray.push(item);
			}

			{
				let item = {} as HolidayTime;
				item.frameNo = ko.observable(holidayTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_344'));
				item.start = ko.observable(null);
				item.preApp = ko.observable(self.application().prePostAtr());
				item.actualTime = ko.observable(null);
				holidayTimeArray.push(item);
			}

			self.holidayTime(holidayTimeArray);
			
		}

		bindOverTime(res: AppHdWorkDispInfo) {
			const self = this;
			let overTimeArray = [];

			for (let i = 0; i < res.overtimeFrameList.length; i++) {
				let item = {} as OverTime;
				item.frameNo = ko.observable(res.overtimeFrameList[i].overtimeWorkFrNo);
				item.frameName = ko.observable(res.overtimeFrameList[i].overtimeWorkFrName);
				item.applicationTime = ko.observable(null);
				item.preTime = ko.observable(null);
				item.actualTime = ko.observable(null);
				overTimeArray.push(item);
			}

			{
				let item = {} as OverTime;
				item.frameNo = ko.observable(overTimeArray.length + 1);
				item.frameName = ko.observable(self.$i18n('KAF005_61'));
				item.applicationTime = ko.observable(null);
				item.preTime = ko.observable(null);
				item.actualTime = ko.observable(null);
				overTimeArray.push(item);
			}

			self.overTime(overTimeArray);
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

		openDialogKdl003() {
			const self = this;
			nts.uk.ui.windows.setShared( 'parentCodes', {
                workTypeCodes: _.map( _.uniqBy( self.dataSource.hdWorkDispInfoWithDateOutput.workTypeList, e => e.workTypeCode ), (item: any) => item.workTypeCode ),
                selectedWorkTypeCode: self.workInfo().workType().code,
                workTimeCodes: _.map( self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode ),
                selectedWorkTimeCode: self.workInfo().workTime().code
            }, true);

			nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml' ).onClosed( function(): any {
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

					let command = {
						companyId: self.$user.companyId,
						dateList: [self.application().appDate()],
						workTypeCode: workType.code,
						workTimeCode: workTime.code,
						startTime: 0,
						endTime: 0,
						appHdWorkDispInfoDto: self.dataSource
					};

					self.$blockui('show');
					self.$ajax(API.selectWorkInfo, command)
						.done((res: AppHdWorkDispInfo) => {
							console.log(res, 'select work info');
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
							self.bindHolidayTime(self.dataSource);
							self.bindOverTime(self.dataSource);

							
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
				startTime: self.workInfo().workHours1.start,
				endTime: self.workInfo().workHours1.end,
				appHdWorkDispInfoDto: self.dataSource
			};
			self.$blockui('show');
			self.$ajax(API.changeWorkHours, command)
				.done((res: AppHdWorkDispInfo) =>{
					console.log(res, 'get break time');
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
			console.log('change date3');
			const self = this;
			let param = {} as ParamHolidayWorkChangeDate;

			param.companyId = self.$user.companyId;
			param.dateList = [self.application().appDate()];
			param.applicationType = AppType.HOLIDAY_WORK_APPLICATION;
			param.appHdWorkDispInfoDto = ko.toJS(self.dataSource);
			self.$blockui('show');
			console.log(param, 'param ne');
			self.$ajax(API.changeAppDate, param)
				.done((res: AppHdWorkDispInfo) => { 

					self.dataSource = res;

					console.log(self.dataSource);

					self.bindOverTimeWorks(self.dataSource);
					self.bindWorkInfo(self.dataSource);
					self.bindRestTime(self.dataSource);
					self.bindHolidayTime(self.dataSource);
					self.bindOverTime(self.dataSource);

				})
				.fail(() => {})
				.always(() => self.$blockui('hide'));
			

		}

		testzz() {
			const self = this;
			console.log(self.application().prePostAtr(), 'status12');
		}

		

		calculate() {
			const self = this;
			console.log('Calculation1');
			
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
				command.preApplicationTime = self.dataSource.appDispInfoStartupOutput.appDispInfoWithDateOutput.opPreAppContentDispDtoLst[0].appHolidayWork.applicationTime;
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
		
			self.$ajax(API.calculate, command)
				.done((res: HolidayWorkCalculationResult) => {
					if (res) {
						let appTimeList = res.applicationTime.applicationTime;
						let holidayTimeArray = self.holidayTime();
						let overTimeArray = self.overTime();
						appTimeList.forEach((appTime: OvertimeApplicationSetting) => {
							holidayTimeArray.forEach((holidayTime: HolidayTime) => {
								if (appTime.frameNo === holidayTime.frameNo() && appTime.attendanceType === 1) {
									holidayTime.start(appTime.applicationTime);
								}
							})
							overTimeArray.forEach((overTime: OverTime) => {
								if (appTime.frameNo === overTime.frameNo() && appTime.attendanceType === 0) {
									overTime.applicationTime(appTime.applicationTime);
								} 
							})
						})
						//A7_13
						overTimeArray[length - 1].applicationTime(res.applicationTime.overTimeShiftNight.overTimeMidNight);
						//A6_28
						for (let item of res.applicationTime.overTimeShiftNight.midNightHolidayTimes) {
							switch (item.legalClf) {
								case 0:
									holidayTimeArray[length-3].start(item.attendanceTime);
									break;
								case 1:
									holidayTimeArray[length-2].start(item.attendanceTime);
									break;
								case 2: 
									holidayTimeArray[length-1].start(item.attendanceTime);
									break;
							}
							
						} 
						
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
			return (formatTime("ClockDay_Short_HM", 10));
		}

		handleConfirmMessage(listMes: any, vmParam: any): any {
			const vm = this;

			return new Promise((resolve: any) => {
				if(_.isEmpty(listMes)) {
					resolve(true);
				}
				let msg = listMes[0].value;

				return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
					.then((value) => {
						if (value === 'yes') {
							return vm.handleConfirmMessage(listMes, vmParam);
						} else {
							resolve(false);
						}
					})
	        });
		}

		// registerData(params: any): any {
		// 	let vm = this;

		// 	return vm.$ajax(API.registerSample, params);
		// }
	}

	const API = {
		startNew: "at/request/application/holidaywork/startNew",
		changeAppDate: "at/request/application/holidaywork/changeAppDate",
		calculate: "at/request/application/holidaywork/calculate",
		selectWorkInfo: "at/request/application/holidaywork/selectWork",
		changeWorkHours:  "at/request/application/holidaywork/changeWorkHours"
	}

	interface AppHdWorkDispInfo {
		dispFlexTime: boolean;
		useInputDivergenceReason: boolean;
		useComboDivergenceReason: boolean;
		workdayoffFrameList: Array<WorkdayoffFrame>;
		otWorkHoursForApplication: AgreeOverTime;
		hdWorkDispInfoWithDateOutput: HdWorkDispInfoWithDateOutput;
		calculationResult: CalculationResult;
		appDispInfoStartupOutput: any;
		overtimeFrameList: Array<OvertimeWorkFrame>;
		holidayWorkAppSet: any;
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
	interface TimeWithDayAttr {

	}

	interface WorkdayoffFrame {
		workdayoffFrNo: number;
		workdayoffFrName: string;
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
		calculatedFlag: boolean;
	}
	
	export enum ScreenMode {
		NEW,
		ACTING
	}
}