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

		constructor() {
			super();
			const vm = this;
			
		}

		created(params: AppInitParam) {
			const vm = this;

			vm.createRestTime();
			vm.createHolidayTime();

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
					if (loadDataFlag) {
						let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
							command = { empList, dateList, appDispInfoStartupOutput };
						return vm.$ajax(API.startNew, command);
					}
				}).then((successData: any) => {
					if (successData) {
						console.log(successData);
						vm.dataSource = successData;

						vm.bindOverTimeWorks(vm.dataSource);
						vm.bindWorkInfo(vm.dataSource);
						vm.bindRestTime(vm.dataSource);
						vm.bindHolidayTime(vm.dataSource);

						if (!_.isEmpty(params)) {
							if (!_.isEmpty(params.baseDate)) {
								
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
			const vm = this;
		}
		
		register() {
			const vm = this;

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
			const { hdWorkDispInfoWithDateOutput } = res;
			let holidayTimeArray = self.holidayTime() as Array<HolidayTime>;
			if (!_.isNil(hdWorkDispInfoWithDateOutput)) {
				let actualApplicationTime = hdWorkDispInfoWithDateOutput.actualApplicationTime;
				if (!_.isNil(actualApplicationTime)) {
					if (!_.isEmpty(actualApplicationTime.applicationTime)) {
						_.forEach(actualApplicationTime.applicationTime, (item: OvertimeApplicationSetting, index) => {
							if (Number(index) < 10) {
								let holidayItem = holidayTimeArray[index] as HolidayTime;
								holidayItem.actualTime(item.applicationTime);
							}
						})
					}
				}
			}
			self.holidayTime(_.clone(holidayTimeArray));
		}

		createRestTime() {
			const self = this;
			console.log('creat rest time2');
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

		createHolidayTime() {
			const self = this;
			let holidayTimeArray = [];
			let length = 10;
			for (let i = 1; i < length + 1; i++) {
				let item = {} as HolidayTime;
				item.frameNo = i.toString();
				item.start = ko.observable(null);
				item.preApp = ko.observable(null);
				item.actualTime = ko.observable(null);
				holidayTimeArray.push(item);
			}
			self.holidayTime(holidayTimeArray);
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

		registerData(params: any): any {
			let vm = this;

			return vm.$ajax(API.registerSample, params);
		}
	}

	const API = {
		startNew: "at/request/application/holidaywork/startNew",
		registerSample: "at/request/application/registerSample",
		checkBeforeRegisterSample: "at/request/application/checkBeforeRegisterSample"
	}

	interface AppHdWorkDispInfo {
		dispFlexTime: boolean;
		useInputDivergenceReason: boolean;
		useComboDivergenceReason: boolean;
		workdayoffFrame: WorkdayoffFrame;
		otWorkHoursForApplication: AgreeOverTime;
		hdWorkDispInfoWithDateOutput: HdWorkDispInfoWithDateOutput;
		calculationResult: CalculationResult;
		appDispInfoStartupOutput: any;
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
		actualApplicationTime: ApplicationTime
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

	

	
}