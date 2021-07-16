module nts.uk.at.view.kaf011 {
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import ApplicationCommon = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import getText = nts.uk.resource.getText;
	import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;
	import windows = nts.uk.ui.windows;

	/** 振出申請 */
	export class RecruitmentApp {
		isInit: KnockoutObservable<boolean> = ko.observable(true);
		appType: number; //0: Rec-振出, 1: Abs-振休
		application: Application = new Application();
		applicationInsert: Application = this.application;
		applicationUpdate: Application = this.application;
		workInformation: WorkInformation = new WorkInformation();
		workingHours: KnockoutObservableArray<TimeZoneWithWorkNo> = ko.observableArray([new TimeZoneWithWorkNo(1)]);

		leaveComDayOffMana: KnockoutObservableArray<SubWorkSubHolidayLinkingMng> = ko.observableArray([]);
		leaveComDayOffManaOld: KnockoutObservableArray<SubWorkSubHolidayLinkingMng> = ko.observableArray([]);

		workTypeSelected = new WorkTypeSelected();
		workTypeList = ko.observableArray([]);
		workTimeDisplay: KnockoutObservable<string> = ko.observable();
		displayInforWhenStarting: any = null;

		//điều kiện hiển thị ※H1
		dk1: KnockoutObservable<boolean> = ko.observable(false);
		// điều kiện hiển thị ※E1
		dk2: KnockoutObservable<boolean> = ko.observable(false);
		// điều kiện hiển thị ※E1-1
		dk3: KnockoutObservable<boolean> = ko.observable(false);
		//
		dk4: KnockoutObservable<boolean> = ko.observable(false);

		started: boolean = false;

		isAScreen: KnockoutObservable<boolean> = ko.observable(true); //true: screen A, false: screen B
		outputMode: KnockoutObservable<number> = ko.observable(1); //DISPLAYMODE(0),EDITMODE(1);
		
		appDateWithInput: KnockoutObservable<string> = ko.observable(null);

		constructor(appType: number, isInit: KnockoutObservable<boolean>, isAScreen?: boolean){
			let self = this;
			if(isAScreen != undefined){
				self.isAScreen(isAScreen);
			}
			self.isInit = isInit;
			self.appType = appType;
			self.workInformation.workType.subscribe((data: string)=>{
				if(data && !self.isInit()){
					let workTypeAfter = _.find(self.workTypeList(), {'workTypeCode': data});
					self.workTypeSelected.update(workTypeAfter);
					self.checkDisplay();
					//only Rec-振出
					if(self.appType == 0 && self.started){
						let workTypeBefore = _.find(self.workTypeList(), {'workTypeCode': self.displayInforWhenStarting.applicationForWorkingDay.workType});
						let payoutSubofHDManagements: any[] = [];
						let command = {
							workTypeBefore: workTypeBefore,
							workTypeAfter: workTypeAfter,
							workTimeCode: self.workInformation.workTime(),
							leaveComDayOffMana: self.leaveComDayOffMana(),
							payoutSubofHDManagements: payoutSubofHDManagements,
						}
						block.invisible();
						ajax("at/request/application/holidayshipment/changeWorkType", command).done((data:any) => {
							if(data.clearManageSubsHoliday){
								self.leaveComDayOffMana([]);
							}
							let w1 = _.find(self.workingHours(), {'workNo': 1});
							let w1n:any = _.find(data.workingHours, {'workNo': 1});
							if(w1n){
								w1.update(w1n.timeZone);
							}else{
								w1.update({startTime: null, endTime: null});
							}
							let w2 = _.find(self.workingHours(), {'workNo': 2});
							let w2n:any = _.find(data.workingHours, {'workNo': 2});
							if(w2n){
								if(w2){
									w2.update(w2n.timeZone);
								}else{
									let tg = new TimeZoneWithWorkNo(2);
									tg.update(w2n.timeZone);
									self.workingHours.push(tg);
								}
							}else if(w2){
								self.workingHours.remove(c => {
									return c.workNo== 2;
								});
							}
						}).always(() => {
							block.clear();
						});
					}
				}
			});
		}

		bindingScreenA(data: any, displayInforWhenStarting: any){
			let self = this;
			self.started = false;
			_.orderBy(data.workTypeList, ['code'], ['asc'])
			self.workTypeList(data.workTypeList);
			self.workInformation.update(data);
			self.displayInforWhenStarting = displayInforWhenStarting;

			let w1 = _.find(self.workingHours(), {'workNo': 1});
			w1.update({startTime: data.startTime, endTime: data.endTime});
			if(data.startTime2 && data.endTime2){
				let w2 = new TimeZoneWithWorkNo(2);
				w2.update({startTime: data.startTime2, endTime: data.endTime2});
				self.workingHours.push(w2);
			}else{
				self.workingHours.remove(c => {
					return c.workNo== 2;
				});
			}
			if(data.workTime){
				let workTime: any = _.find(displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, {'worktimeCode': data.workTime});
				self.workTimeDisplay(data.workTime + ' ' +
					(workTime?(workTime.workTimeDisplayName.workTimeName + ' ') : 'マスタ未登録 ' )+
					moment(Math.floor(data.startTime / 60),'mm').format('mm') + ":" + moment(data.startTime % 60,'mm').format('mm') + getText('KAF011_37') + moment(Math.floor(data.endTime / 60),'mm').format('mm') + ":" + moment(data.endTime % 60,'mm').format('mm'));
			}
			self.checkDisplay();
			self.started = true;

		}

		bindingScreenB(param: any, workTypeList:[], displayInforWhenStarting: any){
			let self = this;
			self.application.update(param.application);
			self.appDateWithInput(getText('KAF011_23', [param.application.appDate, param.application.inputDate]));
			self.workTypeList(workTypeList);
			if (_.filter(self.workTypeList(), {'workTypeCode': param.workInformation.workType}).length == 0) {
				self.workTypeList().push({ workTypeCode: param.workInformation.workType, name: 'マスタ未登録' });
			}
			self.workTypeList(_.sortBy(self.workTypeList(), [ 'workTypeCode' ]));
			self.workInformation.update(param.workInformation);

			self.leaveComDayOffMana(_.map(param.leaveComDayOffMana, (c) =>new SubWorkSubHolidayLinkingMng(c)));
			self.leaveComDayOffManaOld(self.leaveComDayOffMana());

			self.displayInforWhenStarting = displayInforWhenStarting;

			let w1 = _.find(self.workingHours(), {'workNo': 1});
			let time1:any = _.find(param.workingHours, { 'workNo': 1});
			if(time1){
				w1.update(time1.timeZone);
			}
			let w2:any = _.find(param.workingHours, { 'workNo': 2});
			if(w2) {
				let tg = new TimeZoneWithWorkNo(2);
				tg.update(w2.timeZone)
				self.workingHours.push(tg);
			}else{
				self.workingHours.remove(c => {
					return c.workNo == 2;
				});
			}
			if(param.workInformation.workTime != null && time1 != undefined){
				let workTime: any = _.find(displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, {'worktimeCode': param.workInformation.workTime});
				self.workTimeDisplay(param.workInformation.workTime + ' ' +
					(workTime?(workTime.workTimeDisplayName.workTimeName + ' ') : 'マスタ未登録 ' )+
					moment(Math.floor(time1.timeZone.startTime / 60),'mm').format('mm') + ":" + moment(time1.timeZone.startTime % 60,'mm').format('mm') + getText('KAF011_37') + moment(Math.floor(time1.timeZone.endTime / 60),'mm').format('mm') + ":" + moment(time1.timeZone.endTime % 60,'mm').format('mm'));
			}
			self.checkDisplay();
			self.started = true;
		}

		convertTimeToString(data: any){
			let self = this;
			if(data.first && data.first.start){
				self.workTimeDisplay(data.selectedWorkTimeCode + ' ' +
					data.selectedWorkTimeName + ' ' +
					moment(Math.floor(data.first.start / 60),'mm').format('mm') + ":" + moment(data.first.start % 60,'mm').format('mm') + getText('KAF011_37') + moment(Math.floor(data.first.end / 60),'mm').format('mm') + ":" + moment(data.first.end % 60,'mm').format('mm'));
			}else{
				self.workTimeDisplay("");
			}
		}

		checkDisplay(){
			let self = this;
			if(self.displayInforWhenStarting){
				//điều kiện hiển thị ※H1
				self.dk1(self.displayInforWhenStarting.substituteManagement == 1 && self.workTypeSelected.checkH1Ver19());
				// điều kiện hiển thị ※E1
				self.dk2(self.checkE1Common() || (self.workTypeSelected.workAtr == 1 && self.workTypeSelected.checkE12() && self.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 1));
				// điều kiện hiển thị ※E1-1
				self.dk3(self.checkE1Common());
				if(self.displayInforWhenStarting && self.displayInforWhenStarting.appDispInfoStartup.appDetailScreenInfo){
					self.outputMode(self.displayInforWhenStarting.appDispInfoStartup.appDetailScreenInfo.outputMode);
				}
			}

		}
		/**就業時間帯を反映する※1(Phản ánh worktime ※1) 勤務種類：1日の勤務区分//worktype: phân loại đi làm 1 ngày */
		checkE1Common(): boolean{
			let self = this;
			return self.workTypeSelected.workAtr == 1 && self.workTypeSelected.checkE11()
				&& (self.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 1
					|| self.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 2)
		}

		openKDL003() {
			let self = this;
			windows.setShared('parentCodes',{
				workTypeCodes: _.map(self.workTypeList(),'workTypeCode'),
				selectedWorkTypeCode: self.workInformation.workType(),
				selectedWorkTimeCode: self.workInformation.workTime(),
				workTimeCodes: _.map(self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, 'worktimeCode')
			});
			windows.sub.modal( '/view/kdl/003/a/index.xhtml').onClosed(() => {
				let data = windows.getShared('childData');
				if(data){

					self.workInformation.workType(data.selectedWorkTypeCode);
					self.workInformation.workTime(data.selectedWorkTimeCode);

					let w1 = _.find(self.workingHours(), {'workNo': 1});
					w1.update(data.first);
					if(data.second.start && data.second.end){
						let w2 = _.find(self.workingHours(), {'workNo': 2});
						if(w2){
							w2.update(data.second);
						}else{
							let tg = new TimeZoneWithWorkNo(2);
							tg.update(data.second)
							self.workingHours.push(tg);
						}
					}else{
						self.workingHours.remove(c => {
							return c.workNo== 2;
						});
					}

					self.convertTimeToString(data);
				}
			});
		}

		openKDL036() {
			let self = this;
			if(self.application.appDate() == "" ){
				if(self.appType == 0){
					$("#recAppDate").trigger("validate");
				}else{
					$("#absAppDate").trigger("validate");
				}
			}else{
				windows.setShared('KDL036_PARAMS', {
					employeeId: self.application.employeeIDLst()[0],
					period: {
						startDate: moment(self.application.appDate()).format('YYYY/MM/DD'),
						endDate: moment(self.application.appDate()).format('YYYY/MM/DD')
					},
					daysUnit: self.workTypeSelected.workAtr == 0 ? 1: 0.5,
					targetSelectionAtr: 1, //申請
					actualContentDisplayList: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opActualContentDisplayLst,
					managementData: self.leaveComDayOffMana()
				});
				windows.sub.modal( '/view/kdl/036/a/index.xhtml').onClosed(() => {
					let data = windows.getShared('KDL036_RESULT');
					if(data){
						let tg: SubWorkSubHolidayLinkingMng[] = [];
						_.forEach(data, item =>{
							tg.push(new SubWorkSubHolidayLinkingMng(item));
						});
						self.leaveComDayOffMana(tg);
					}
				});
			}
		}

	}

	/** 振休申請 */
	export class AbsenceLeaveApp extends RecruitmentApp {
		isInit: KnockoutObservable<boolean> = ko.observable(true);
		workChangeUse: KnockoutObservable<boolean> = ko.observable(false);
		changeSourceHoliday: KnockoutObservable<string> = ko.observable();
		payoutSubofHDManagements: KnockoutObservableArray<SubWorkSubHolidayLinkingMng> = ko.observableArray([]);
		payoutSubofHDManagementsOld: KnockoutObservableArray<SubWorkSubHolidayLinkingMng> = ko.observableArray([]);

		constructor(appType: number, isInit: KnockoutObservable<boolean>, isAScreen?: boolean){
			super(appType, isInit, isAScreen);
			let self = this;
			self.isInit = isInit;
			self.workInformation.workType.subscribe((data: string)=>{
				//only Abs-振休
				if(data && self.appType == 1 && !self.isInit() && self.started){
					let workTypeAfter = _.find(self.workTypeList(), {'workTypeCode': data});
					let workTypeBefore = _.find(self.workTypeList(), {'workTypeCode': self.displayInforWhenStarting.applicationForHoliday.workType});
					let command = {
						workTypeBefore: workTypeBefore,
						workTypeAfter: workTypeAfter,
						workTimeCode: self.workInformation.workTime(),
						leaveComDayOffMana: self.leaveComDayOffMana(),
						payoutSubofHDManagements: self.payoutSubofHDManagements(),
					}
					block.invisible();
					ajax("at/request/application/holidayshipment/changeWorkType", command).done((data:any) => {
						if(data.clearManageSubsHoliday){
							self.leaveComDayOffMana([]);
						}
						if(data.clearManageHolidayString){
							self.payoutSubofHDManagements([]);
						}
						let w1 = _.find(self.workingHours(), {'workNo': 1});
						let w1n:any = _.find(data.workingHours, {'workNo': 1});
						if(w1n){
							w1.update(w1n.timeZone);
						}else{
							w1.update({startTime: null, endTime: null});
						}
						let w2 = _.find(self.workingHours(), {'workNo': 2});
						let w2n:any = _.find(data.workingHours, {'workNo': 2});
						if(w2n){
							if(w2){
								w2.update(w2n.timeZone);
							}else{
								let tg = new TimeZoneWithWorkNo(2);
								tg.update(w2n.timeZone);
								self.workingHours.push(tg);
							}
						}else if(w2){
							self.workingHours.remove(c => {
								return c.workNo== 2;
							});
						}
					}).always(() => {
						block.clear();
					});;
				}
			});
		}

		bindingScreenBAbs(param: any, workTypeList: [], displayInforWhenStarting: any){
			let self = this;
			super.bindingScreenB(param, workTypeList, displayInforWhenStarting);
			self.payoutSubofHDManagements(_.map(param.payoutSubofHDManagements, (c) =>new SubWorkSubHolidayLinkingMng(c)));
			self.payoutSubofHDManagementsOld(self.payoutSubofHDManagements());
			self.workChangeUse(param.workChangeUse);
			self.changeSourceHoliday(param.changeSourceHoliday);
		}

		openKDL035() {
			let self = this;
			if(self.application.appDate() == "" ){
				$("#absAppDate").trigger("validate");
			}else{
				windows.setShared('KDL035_PARAMS', {
					employeeId: self.application.employeeIDLst()[0],
					period: {
						startDate: moment(self.application.appDate()).format('YYYY/MM/DD'),
						endDate: moment(self.application.appDate()).format('YYYY/MM/DD')
					},
					daysUnit: self.workTypeSelected.workAtr == 0 ? 1: 0.5,
					targetSelectionAtr: 1,
					actualContentDisplayList: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opActualContentDisplayLst,
					managementData: self.payoutSubofHDManagements()
				});
				windows.sub.modal( '/view/kdl/035/a/index.xhtml').onClosed(() => {
					let data = windows.getShared('KDL035_RESULT');
					if(data){
						let tg: SubWorkSubHolidayLinkingMng[] = [];
						_.forEach(data, item =>{
							tg.push(new SubWorkSubHolidayLinkingMng(item));
						});
						self.payoutSubofHDManagements(tg);
					}
				});
			}
		}
		openKAF011C() {
			let self = this;
			windows.setShared('KAF011C',self.displayInforWhenStarting);
			windows.sub.modal( '/view/kaf/011/c/index.xhtml').onClosed(() => {
				let data = windows.getShared('KAF011C_RESLUT');
				let viewModelParent: any = nts.uk.ui._viewModel.content;
				if(data){
					let cacheLst = __viewContext.transferred.value.listAppMeta,
						index = _.indexOf(cacheLst, viewModelParent.currentApp()) + 1,
						preLst = _.slice(cacheLst, 0, index),
						afterLst = _.slice(cacheLst, index);
					__viewContext.transferred.value.listAppMeta = _.concat(_.concat(preLst, [data.appID]), afterLst);
					viewModelParent.listApp(__viewContext.transferred.value.listAppMeta);
					viewModelParent.currentApp(data.appID);
					viewModelParent.loadData();
				}
				console.log(data);
			});
		}
	}

	export class Application extends ApplicationCommon{
		constructor(){
			super(AppType.COMPLEMENT_LEAVE_APPLICATION);
		}
		update(param: any){
			let self = this;
			self.appID(param.appID);
			self.prePostAtr(param.prePostAtr);
			self.employeeIDLst([param.employeeID]);
			self.appDate(param.appDate);
			self.opAppReason(param.opAppReason);
			self.opAppStandardReasonCD(param.opAppStandardReasonCD);
			self.opReversionReason(param.opReversionReason);
			self.opAppStartDate(param.opAppStartDate);
			self.opAppEndDate(param.opAppEndDate);
			self.opStampRequestMode(param.opStampRequestMode);
		}
	}

	export class WorkInformation {
		workType: KnockoutObservable<string> = ko.observable();
		workTime: KnockoutObservable<string> = ko.observable();
		constructor(){}
		update(param: any){
			let self = this;
			self.workType(param.workType);
			self.workTime(param.workTime);
		}
	}

	export class TimeZoneWithWorkNo {
		workNo: number;
		timeZone: TimeZone = new TimeZone();
		constructor(workNo: number){
			let self = this;
			self.workNo = workNo;
		}
		update(timeZone: any) {
			if(timeZone){
				let self = this;
				self.timeZone.update(timeZone);
			}
		}

	}

	export class TimeZone {
		startTime: KnockoutObservable<number> = ko.observable();
		endTime: KnockoutObservable<number> = ko.observable();
		constructor(){
		}
		update(param: any){
			let self = this;
			self.startTime(param.startTime || param.start);
			self.endTime(param.endTime || param.end);
		}

	}

	export class Comment {
		subHolidayComment: KnockoutObservable<string> = ko.observable();
		subHolidayColor: KnockoutObservable<string> = ko.observable();
		subHolidayBold: KnockoutObservable<boolean> = ko.observable(false);
		subWorkComment: KnockoutObservable<string> = ko.observable();
		subWorkColor: KnockoutObservable<string> = ko.observable();
		subWorkBold: KnockoutObservable<boolean> = ko.observable(false);
		constructor(){}
		update(param: any){
			let self = this;
			self.subHolidayComment(param.subHolidayComment);
			self.subHolidayColor(param.subHolidayColor);
			self.subHolidayBold(param.subHolidayBold);
			self.subWorkComment(param.subWorkComment);
			self.subWorkColor(param.subWorkColor);
			self.subWorkBold(param.subWorkBold);
		}
	}

	export class SubWorkSubHolidayLinkingMng {
		// 社員ID
		sid: string;
		// 逐次休暇の紐付け情報 . 発生日
		outbreakDay: Date;
		// 逐次休暇の紐付け情報 . 使用日
		dateOfUse: Date;
		// 逐次休暇の紐付け情報 . 使用日数
		dayNumberUsed: number;
		// 逐次休暇の紐付け情報 . 対象選択区分
		targetSelectionAtr: number;
		constructor(param: any){
			let self = this;
			self.sid = param.employeeId || param.sid;
			self.outbreakDay = new Date(param.outbreakDay);
			self.dateOfUse = new Date(param.dateOfUse);
			self.dayNumberUsed = param.dayNumberUsed;
			self.targetSelectionAtr = param.targetSelectionAtr;
		}
	}

	export class WorkTypeSelected {
		workAtr: number;
		morningCls: number;
		afternoonCls: number;
		constructor(){}
		update(param: any){
			let self = this;
			if(param){
				self.workAtr = param.workAtr;
				self.morningCls = param.morningCls;
				self.afternoonCls = param.afternoonCls;
			}
		}
		checkH1Ver19(): boolean{
			let self = this;
			return self.workAtr == 1 && (self.morningCls == 6 || self.afternoonCls == 6);
		}
		/** 半日振休＋出勤系 */
		checkE11():boolean{
			let self = this;
			return (self.morningCls == 8 && self.afternoonCls == 0) || (self.morningCls == 0 && self.afternoonCls == 8)
		}
		/** 半日振休＋休暇系 */
		checkE12():boolean{
			let self = this;
			return (self.morningCls == 8 && (
						self.afternoonCls == 1
						|| self.afternoonCls == 2
						|| self.afternoonCls == 3
						|| self.afternoonCls == 4
						|| self.afternoonCls == 5
						|| self.afternoonCls == 6
						|| self.afternoonCls == 9
					)
				)
				|| (self.afternoonCls == 8 && (
						self.morningCls == 1
						|| self.morningCls == 2
						|| self.morningCls == 3
						|| self.morningCls == 4
						|| self.morningCls == 5
						|| self.morningCls == 6
						|| self.morningCls == 9
					)
				)
		}
	}

	export class DisplayInforWhenStarting {
		//振出申請起動時の表示情報
		applicationForWorkingDay: any;
		//申請表示情報
		appDispInfoStartup: any;
		//振休申請起動時の表示情報
		applicationForHoliday: any;
		//振休残数情報
		remainingHolidayInfor: any;
		//振休振出申請設定
		substituteHdWorkAppSet: any;
		//振休紐付け管理区分
		holidayManage: number;
		//代休紐付け管理区分
		substituteManagement: number;
		//振休申請の反映
		workInfoAttendanceReflect: any;
		//振出申請の反映
		substituteWorkAppReflect: any;
		//振休申請
		abs: any;
		//振出申請
		rec: any;
		represent: boolean;
		constructor(param: any){
			let self = this;
			self.applicationForWorkingDay = param.applicationForWorkingDay;
			self.appDispInfoStartup = param.appDispInfoStartup;
			self.applicationForHoliday = param.applicationForHoliday;
			self.remainingHolidayInfor = param.remainingHolidayInfor;
			self.substituteHdWorkAppSet = param.substituteHdWorkAppSet;
			self.holidayManage = param.holidayManage;
			self.substituteManagement = param.substituteManagement;
			self.workInfoAttendanceReflect = param.workInfoAttendanceReflect;
			self.substituteWorkAppReflect = param.substituteWorkAppReflect;
			self.abs = param.abs;
			self.rec = param.rec;
			self.represent = param.represent;
			if(param.abs){
				self.abs.leaveComDayOffMana = _.map(param.abs.leaveComDayOffMana, (c) => new SubWorkSubHolidayLinkingMng(c));
				self.abs.payoutSubofHDManagements = _.map(param.abs.payoutSubofHDManagements, (c) => new SubWorkSubHolidayLinkingMng(c));
			}
			if(param.rec){
				self.rec.leaveComDayOffMana = _.map(param.rec.leaveComDayOffMana, (c) =>new SubWorkSubHolidayLinkingMng(c));
			}
		}
	}

}