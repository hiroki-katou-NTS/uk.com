module nts.uk.at.view.kaf005.a.viewmodel {
	import OverTime = nts.uk.at.view.kaf005.shr.viewmodel.OverTime;
	import HolidayTime = nts.uk.at.view.kaf005.shr.viewmodel.HolidayTime;
	import RestTime = nts.uk.at.view.kaf005.shr.viewmodel.RestTime;
	import WorkHours = nts.uk.at.view.kaf005.shr.work_info.viewmodel.WorkHours;
	import Work = nts.uk.at.view.kaf005.shr.work_info.viewmodel.Work;
	import WorkInfo = nts.uk.at.view.kaf005.shr.work_info.viewmodel.WorkInfo;
	import OvertimeWork = nts.uk.at.view.kaf005.shr.header.viewmodel.OvertimeWork;
	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;
	import formatTime = nts.uk.time.format.byId;
	
	@bean()
    class Kaf005AViewModel extends Kaf000AViewModel {
	
		appType: KnockoutObservable<number> = ko.observable(AppType.OVER_TIME_APPLICATION);
		application: KnockoutObservable<Application>;
		isSendMail: KnockoutObservable<Boolean>;	
		isAgentMode : KnockoutObservable<boolean> = ko.observable(false);
		overTimeWork: KnockoutObservableArray<OvertimeWork> = ko.observableArray([]);
		workInfo: KnockoutObservable<WorkInfo> = ko.observable(null);
		restTime: KnockoutObservableArray<RestTime> = ko.observableArray([]);
		holidayTime: KnockoutObservableArray<HolidayTime> = ko.observableArray([]);
		overTime: KnockoutObservableArray<OverTime> = ko.observableArray([]);
		dataSource: DisplayInfoOverTime;
		visibleModel: VisibleModel;
		name: KnockoutObservable<string> = ko.observable('GGGGGGGGGG');
		
		created(params: AppInitParam) {		
			// new 
			const vm = this;
			vm.application = ko.observable(new Application(ko.toJS(vm.appType)));
			vm.createRestTime(vm.restTime);
			vm.createHolidayTime(vm.holidayTime);
			vm.createOverTime(vm.overTime);
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
			vm.isSendMail = ko.observable(false);
			vm.$blockui("show");
			// load setting common KAF000
			vm.loadData(empLst, dateLst, vm.appType())
			.then((loadDataFlag: any) => {
				vm.application().appDate.subscribe(value => {
                    console.log(value);
                    if (value) {
                        vm.changeDate();
                    }
                });
				if (loadDataFlag) {
					let param1 = {
							
					} as FirstParam;
					param1.companyId = vm.$user.companyId;
					// param1.dateOp = '2020/11/13';
					param1.overtimeAppAtr = OvertimeAppAtr.EARLY_OVERTIME;
					param1.appDispInfoStartupDto = ko.toJS(vm.appDispInfoStartupOutput);
					param1.startTimeSPR = 100;
					param1.endTimeSPR = 200;
					param1.isProxy = true;
					let command = {
						companyId: param1.companyId,
						dateOp: param1.dateOp,
						overtimeAppAtr: param1.overtimeAppAtr,
						appDispInfoStartupDto: param1.appDispInfoStartupDto,
						startTimeSPR: param1.startTimeSPR,
						endTimeSPR: param1.endTimeSPR,
						isProxy: param1.isProxy,
					};
					// load setting đơn xin
					return vm.$ajax(API.start, command);
				}
			}).then((successData: any) => {
				if (successData) {
					vm.dataSource = successData;
					vm.bindOverTimeWorks(vm.dataSource);
					vm.bindWorkInfo(vm.dataSource);
					vm.bindRestTime(vm.dataSource);
					vm.bindHolidayTime(vm.dataSource);
					vm.bindOverTime(vm.dataSource);
				}
			}).fail((failData: any) => {
				// xử lý lỗi nghiệp vụ riêng
				vm.handleErrorCustom(failData).then((result: any) => {
					if(result) {
						// xử lý lỗi nghiệp vụ chung
						vm.handleErrorCommon(failData);
					}
				});
			}).always(() => {
				vm.$blockui("hide"); 
				$('#kaf000-a-component4-singleDate').focus();
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
		
		mounted() {
			const self = this;
			
		}
		
		createRestTime(restTime: KnockoutObservableArray<RestTime>) {
			const self = this;
			let restTimeArray = [];
			let length = 10;
			for (let i = 1; i < length + 1; i++) {
				let item = {} as RestTime;
				item.frameNo = String(i);
				item.start = ko.observable(null);
				item.end = ko.observable(null);
				restTimeArray.push(item);
			}
			restTime(restTimeArray);
		}
		
		createHolidayTime(holidayTime: KnockoutObservableArray<RestTime>) {
			const self = this;
			let holidayTimeArray = [];
			let length = 10;
			for (let i = 1; i < length + 1; i++) {
				let item = {} as HolidayTime;
				item.frameNo = String(i);
				item.start = ko.observable(null);
				item.preApp = ko.observable(null);
				item.actualTime = ko.observable(null);
				holidayTimeArray.push(item);
			}
			holidayTime(holidayTimeArray);
		}
		createOverTime(overTime: KnockoutObservableArray<OverTime>) {
			const self = this;
			let overTimeArray = [];
			let length = 10;
			for (let i = 1; i < length + 1; i++) {
				let item = {} as OverTime;
				item.frameNo = String(i);
				item.applicationTime = ko.observable(null);
				item.preTime = ko.observable(null);
				item.actualTime = ko.observable(null);
				overTimeArray.push(item);
			}
			overTime(overTimeArray);
		}

		changeDate() {
			console.log('change date');
			const self = this;
			let param1 = {
							
			} as FirstParam;
			param1.companyId = self.$user.companyId;
			param1.dateOp = ko.toJS(self.appDispInfoStartupOutput).appDispInfoWithDateOutput.baseDate;
			param1.overtimeAppAtr = OvertimeAppAtr.EARLY_OVERTIME;
			param1.appDispInfoStartupDto = ko.toJS(self.appDispInfoStartupOutput);
			param1.startTimeSPR = 100;
			param1.endTimeSPR = 200;
			let command = {
				companyId: param1.companyId,
				dateOp: param1.dateOp,
				overtimeAppAtr: param1.overtimeAppAtr,
				appDispInfoStartupDto: param1.appDispInfoStartupDto,
				startTimeSPR: param1.startTimeSPR,
				endTimeSPR: param1.endTimeSPR,
				overTimeAppSet: self.dataSource.infoNoBaseDate.overTimeAppSet,
				worktypes: self.dataSource.infoBaseDateOutput.worktypes
			}
			self.$ajax(API.changeDate, command)
				.done((res: DisplayInfoOverTime) => {
					self.dataSource.infoWithDateApplicationOp = res.infoWithDateApplicationOp;
					self.dataSource.calculationResultOp = res.calculationResultOp;
					self.dataSource.workdayoffFrames = res.workdayoffFrames;
					
					self.bindOverTimeWorks(self.dataSource);
					self.bindWorkInfo(self.dataSource);
					self.bindRestTime(self.dataSource);
					self.bindHolidayTime(self.dataSource);
					self.bindOverTime(self.dataSource);
					
				})
				.fail((res: any) => {
					
				})
				.always(() => self.$blockui('hide'));
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
					return vm.$ajax('at', API.checkBefore, ["Msg_26"]);
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
		// header
		bindOverTimeWorks(res: DisplayInfoOverTime) {
			const self = this;
			let overTimeWorks = [];
			{
				let item = new OvertimeWork();
				let currentMonth = res.infoNoBaseDate.agreeOverTimeOutput.currentMonth;
				item.yearMonth = ko.observable(currentMonth);
				overTimeWorks.push(item);
			}
			{
				let item = new OvertimeWork(); 
				let nextMonth = res.infoNoBaseDate.agreeOverTimeOutput.nextMonth;
				item.yearMonth = ko.observable(nextMonth);
				overTimeWorks.push(item);
			}
			self.overTimeWork(overTimeWorks);
		}
		//  work-info 
		bindWorkInfo(res: DisplayInfoOverTime) {
			const self = this;
			let infoWithDateApplication = res.infoWithDateApplicationOp as InfoWithDateApplication;
			let workInfo = {} as WorkInfo;
			let workType = {} as Work;
			let workTime = {} as Work;
			let workHours1 = {} as WorkHours;
			let workHours2 = {} as WorkHours;
			if (!_.isNil(infoWithDateApplication)) {
				workType.code = infoWithDateApplication.workTypeCD;
				if (!_.isNil(workType.code)) {
					let workTypeList = res.infoBaseDateOutput.worktypes as Array<WorkType>;
					let item = _.find(workTypeList, (item: WorkType) => item.workTypeCode == workType.code)
					if (!_.isNil(item)) {
						workType.name = item.name;									
					}
				} else {
					workType.name = self.$i18n('KAF_005_345');
				}
				workTime.code = infoWithDateApplication.workTimeCD;
				if (!_.isNil(workTime.code)) {
					let workTimeList = res.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst as Array<WorkTime>;
					if (!_.isEmpty(workTimeList)) {
						let item = _.find(workTimeList, (item: WorkTime) => item.workTimeCode == workTime.code);
						if (!_.isNil(item)) {
							workTime.name  = item.workTimeDisplayName.workTimeName;
						}
					}
				} else {
					workTime.name = self.$i18n('KAF_005_345');
				}
				// set input time
				let workHoursDto = infoWithDateApplication.workHours;
				if (workHoursDto) {
					workHours1.start = ko.observable(workHoursDto.startTimeOp1);
					workHours1.end = ko.observable(workHoursDto.endTimeOp1);
					workHours2.start = ko.observable(workHoursDto.startTimeOp2);
					workHours2.end = ko.observable(workHoursDto.endTimeOp2);
				} else {
					workHours1.start = ko.observable(null);
					workHours1.end = ko.observable(null);
					workHours2.start = ko.observable(null);
					workHours2.end = ko.observable(null);
				}
				
			}
			workInfo.workType = ko.observable(workType);		
			workInfo.workTime = ko.observable(workTime);
			workInfo.workHours1 = workHours1;
			workInfo.workHours2 = workHours2;
			
			self.workInfo(workInfo);
		}
		
		bindRestTime(res: DisplayInfoOverTime) {
			const self = this;
			let infoWithDateApplication = res.infoWithDateApplicationOp as InfoWithDateApplication;
			let restTimeArray = self.restTime() as Array<RestTime>;
			if(!_.isNil(infoWithDateApplication)) {
				let breakTime = infoWithDateApplication.breakTime;
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
		
		bindOverTime(res: DisplayInfoOverTime) {
			const self = this;
			let overTimeArray = self.overTime() as Array<OverTime>;
			
			// A6_8
			let calculationResultOp = res.calculationResultOp;
			if (!_.isNil(calculationResultOp)) {
				if (!_.isEmpty(calculationResultOp.applicationTimes)) {
					let applicationTime = calculationResultOp.applicationTimes[0].applicationTime;
					if (!_.isEmpty(applicationTime)) {
						_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
							let findOverTimeArray = _.find(overTimeArray, {frameNo: item.frameNo}) as OverTime;
							if (!findOverTimeArray && item.attendanceType == AttendanceType.NORMALOVERTIME) {
								findOverTimeArray.applicationTime(item.applicationTime);
							}
						});
					}
				}
			}
			// A6_9
			
			let opPreAppContentDisplayLst = res.appDispInfoStartup.appDispInfoWithDateOutput.opPreAppContentDispDtoLst;
			if (!_.isEmpty(opPreAppContentDisplayLst)) {
				let apOptional = opPreAppContentDisplayLst[0].apOptional;
				if (apOptional) {
					let applicationTime = apOptional.applicationTime;
					if (!_.isEmpty(applicationTime)) {
						_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
							let findOverTimeArray = _.find(overTimeArray, {frameNo: item.frameNo}) as HolidayTime;
							
							if (!findOverTimeArray && item.attendanceType == AttendanceType.NORMALOVERTIME) {
								findOverTimeArray.preApp(item.applicationTime);
							}	
						})
				}
				}
			}
			// A6_11
			let infoWithDateApplicationOp = res.infoWithDateApplicationOp;
			if (!_.isNil(infoWithDateApplicationOp)) {
				if (!_.isNil(infoWithDateApplicationOp.applicationTime)) {
					let applicationTime = infoWithDateApplicationOp.applicationTime.applicationTime;
					if (!_.isEmpty(applicationTime)) {
						_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
							let findOverTimeArray = _.find(overTimeArray, {frameNo: item.frameNo}) as HolidayTime;
							
							if (!findOverTimeArray && item.attendanceType == AttendanceType.NORMALOVERTIME) {
								findOverTimeArray.actualTime(item.applicationTime);
							}	
						})
					}
				}
			}
			
			
			
			
		}
		
		bindHolidayTime(res: DisplayInfoOverTime) {
			const self = this;
			let holidayTimeArray = self.holidayTime() as Array<HolidayTime>;
			let workdayoffFrames = res.workdayoffFrames as Array<WorkdayoffFrame>;

			let calculationResultOp = res.calculationResultOp;
			// A7_7
			if (!_.isEmpty(workdayoffFrames)) {
				_.forEach(workdayoffFrames, (item: WorkdayoffFrame) => {
					let findHolidayTimeArray = _.find(holidayTimeArray, {frameNo: item.workdayoffFrNo}) as HolidayTime;
					if (!findHolidayTimeArray) {
						findHolidayTimeArray.frameNo = item.workdayoffFrName;
					}
				})
			}
			
			
			 // A7_8
			if (!_.isEmpty(calculationResultOp)) {
				
				if (!_.isEmpty(calculationResultOp.applicationTimes)) {
					let applicationTime = calculationResultOp.applicationTimes[0].applicationTime;
					if (!_.isEmpty(applicationTime)) {
						_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
							let findHolidayTimeArray = _.find(holidayTimeArray, {frameNo: item.frameNo}) as HolidayTime;
							
							if (!findHolidayTimeArray && item.attendanceType == AttendanceType.BREAKTIME) {
								findHolidayTimeArray.start(item.applicationTime);
							}	
						})
					}
				}
				
			}
			// A7_9
			// 申請表示情報．申請表示情報(基準日関係あり)．表示する事前申請内容．残業申請．申請時間．申請時間．申請時間
			let opPreAppContentDisplayLst = res.appDispInfoStartup.appDispInfoWithDateOutput.opPreAppContentDispDtoLst;
			if (!_.isEmpty(opPreAppContentDisplayLst)) {
				let apOptional = opPreAppContentDisplayLst[0].apOptional;
				if (apOptional) {
					let applicationTime = apOptional.applicationTime;
					if (!_.isEmpty(applicationTime)) {
					_.forEach(applicationTime, (item: OvertimeApplicationSetting) => {
						let findHolidayTimeArray = _.find(holidayTimeArray, {frameNo: item.frameNo}) as HolidayTime;
						
						if (!findHolidayTimeArray && item.attendanceType == AttendanceType.BREAKTIME) {
							findHolidayTimeArray.preApp(item.applicationTime);
						}	
					})
				}
				}
			}
			
			// A7_10
			// 申請日に関係する情報．実績の申請時間．申請時間．申請時間
			
			self.holidayTime(_.clone(holidayTimeArray));
			
		}
		
		createVisibleModel(res: DisplayInfoOverTime) {
			let visibleModel = new VisibleModel() as VisibleModel;
			// 「残業申請の表示情報．基準日に関係しない情報．残業申請設定．申請詳細設定．時刻計算利用区分」= する
			let c7  = res.infoNoBaseDate.overTimeAppSet.applicationDetailSetting.timeInputUse == NotUseAtr.USE
			visibleModel.c7(c7);
			// ※7 = ○　OR　※18-1 = ○
			let c18 = false;
			visibleModel.c18(c18);
			
			// 「残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．残業申請．実績の勤務情報へ反映する」= する
			let c26 = res.infoNoBaseDate.overTimeReflect.overtimeWorkAppReflect.reflectActualWorkAtr == NotUseAtr.USE;
			visibleModel.c26(c26);
			// ※7 = ○　AND 「残業申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
			let c29 = c7 && false;
			visibleModel.c29(c29);
			return visibleModel;
		}
		
		openDialogKdl003() {
			
		}
		getFormatTime(number: number) {
			if (_.isNil(number)) return '';
			return (formatTime("ClockDay_Short_HM", 10));
		}
		
		
		
	
	}
	const API = {
		start: 'at/request/application/overtime/start',
		changeDate: 'at/request/application/overtime/changeDate',
		checkBefore: '',
		register: ''
	}
	class VisibleModel {
		
		c6: KnockoutObservable<Boolean> = ko.observable(false);
		
		c7: KnockoutObservable<Boolean> = ko.observable(false);
		c18: KnockoutObservable<Boolean> = ko.observable(false);
		
		c26: KnockoutObservable<Boolean> = ko.observable(false);
		
		c29: KnockoutObservable<Boolean> = ko.observable(false);
		
		
		constructor() {
			
		}
	}
	enum NotUseAtr {
		Not_USE,
		USE
	}
	interface DisplayInfoOverTime {
		infoBaseDateOutput: InfoBaseDateOutput;
		infoNoBaseDate: InfoNoBaseDate;
		workdayoffFrames: Array<WorkdayoffFrame>;
		overtimeAppAtr: OvertimeAppAtr;
		appDispInfoStartup: any;
		isProxy: Boolean;
		calculationResultOp?: CalculationResult;
		infoWithDateApplicationOp?: InfoWithDateApplication;
	}
	interface WorkdayoffFrame {
		workdayoffFrNo: number;
		workdayoffFrName: string;
	}
	interface CalculationResult {
		flag: number;
		overTimeZoneFlag: number;
		overStateOutput: any;
		applicationTimes: Array<ApplicationTime>;
	}
	interface InfoWithDateApplication {
		workTypeCD?: string;
		workTimeCD?: string;
		workHours?: WorkHoursDto;
		breakTime?: BreakTimeZoneSetting;
		applicationTime?: ApplicationTime;
	}
	interface BreakTimeZoneSetting {
		timeZones?: Array<TimeZone>;
	}
	interface TimeZone {
		start: number;
		end: number;
	}
	interface WorkHoursDto {
		startTimeOp1: number;
		endTimeOp1: number;
		startTimeOp2: number;
		endTimeOp2: number;
	}
	interface InfoBaseDateOutput {
		worktypes: Array<WorkType>;
		quotaOutput: any;
	}
	interface WorkType {
		workTypeCode: string;
		name: string;
	}
	
	interface WorkTime {
		workTimeCode: string;
		workTimeDisplayName: WorkTimeDisplayName;
	}
	interface WorkTimeDisplayName {
		workTimeName: string;
	}
	interface InfoNoBaseDate {
		overTimeReflect: any;
		overTimeAppSet: any;
		agreeOverTimeOutput: AgreeOverTimeOutput;
		divergenceReasonInputMethod: Array<any>;
		divergenceTimeRoot: Array<any>;
	}
	interface AgreeOverTimeOutput {
		detailCurrentMonth: AgreementTimeImport;
		detailNextMonth: AgreementTimeImport;
		currentMonth: string;
		nextMonth: string;
	}
	interface AgreementTimeImport {
		employeeId: string;
		confirmed?: AgreeTimeOfMonthExport;
		afterAppReflect?: AgreeTimeOfMonthExport;
		confirmedMax?: AgreMaxTimeOfMonthExport;
		afterAppReflectMax?: AgreMaxTimeOfMonthExport;
		errorMessage?: string;
	}
	interface AgreeTimeOfMonthExport {
		agreementTime: number;
		limitErrorTime: number;
		limitAlarmTime: number
		exceptionLimitErrorTime?: number;
		exceptionLimitAlarmTime?: number;
		status: number
	}
	interface AgreMaxTimeOfMonthExport {
		agreementTime: number;
		maxTime: number;
		status: number;
	}
	enum OvertimeAppAtr {
		
		EARLY_OVERTIME,
		NORMAL_OVERTIME,
		EARLY_NORMAL_OVERTIME
	}
	enum AttendanceType {
		
		NORMALOVERTIME,
		BREAKTIME,
		BONUSPAYTIME,
		BONUSSPECIALDAYTIME
	}
	
	interface FirstParam { // start param
		companyId: string; // 会社ID
		appType?: number; // 申請種類
		sids?: Array<string>; // 申請者リスト
		dates?: Array<string>; // 申請対象日リスト
		mode: number; // 新規詳細モード
		dateOp?: string // 申請日
		overtimeAppAtr: number; // 残業申請区分
		appDispInfoStartupDto: any; // 申請表示情報
		startTimeSPR?: number; // SPR連携の開始時刻
		endTimeSPR?: number; // SPR連携の終了時刻
		isProxy: boolean; // 代行申請か
	}
	
	interface SecondParam { // start param
		companyId: string; // 会社ID
		employeeId: string; // 社員ID
		appDate: string; // 申請日
		prePostInitAtr: number; // 事前事後区分
		overtimeLeaveAppCommonSet: OvertimeLeaveAppCommonSet; // 残業休出申請共通設定
		advanceApplicationTime: ApplicationTime; // 事前の申請時間
		achivementApplicationTime: ApplicationTime; // 実績の申請時間
		workContent: WorkContent; // 勤務内容
	}
	interface OvertimeLeaveAppCommonSet {
		preExcessDisplaySetting: number; // 事前超過表示設定
		extratimeExcessAtr: number; // 時間外超過区分
		extratimeDisplayAtr: number; // 時間外表示区分
		performanceExcessAtr: number; // 実績超過区分
		checkOvertimeInstructionRegister: number; // 登録時の指示時間超過チェック
		checkDeviationRegister: number; // 登録時の乖離時間チェック
		overrideSet: number; // 実績超過打刻優先設定
		
	}
	interface ApplicationTime {
		applicationTime: Array<OvertimeApplicationSetting>; //  申請時間
		flexOverTime: number; // フレックス超過時間
		overTimeShiftNight: OverTimeShiftNight; // 就業時間外深夜時間
		anyItem: Array<AnyItemValue>; // 任意項目
		reasonDissociation: Array<any>; // 乖離理由
	}
	interface OvertimeApplicationSetting {
		frameNo: number; 
		attendanceType: number;
		applicationTime: number
	}
	interface OverTimeShiftNight {
		midNightHolidayTimes: Array<any>;
		midNightOutSide: number;
		overTimeMidNight: number;
	}
	interface AnyItemValue {
		itemNo: number;
		times: number;
		amount: number;
		time: number
	}
	interface ReasonDivergence {
		
		reason: DivergenceReason;
		reasonCode: string;
		diviationTime: number;
	}
	interface DivergenceReason {
		
	}
	interface WorkContent {
		workTypeCode: string;
		workTimeCode: string;
		timeZones: Array<TimeZone>;
		breakTimes: Array<BreakTimeSheet>;
	}
	interface TimeZone {
		start: number;
		end: number;
	}
	interface BreakTimeSheet {
		breakFrameNo: number;
		startTime: number;
		endTime: number;
		breakTime: number;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}