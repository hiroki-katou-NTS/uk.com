module nts.uk.at.view.kaf011 {
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import ApplicationCommon = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import getText = nts.uk.resource.getText;
	/** 振出申請 */
	export class RecruitmentApp {
		appType: number; //0: Rec-振出, 1: Abs-振休
		appID: string;
		application: Application = new Application();
		workingHours: TimeZoneWithWorkNo[] = [];
		workTimeDisplay: KnockoutObservable<string> = ko.observable('');
		workingHours1: TimeZoneWithWorkNo = new TimeZoneWithWorkNo(1, this.collectWorkingHours, this);
		workingHours2: TimeZoneWithWorkNo = new TimeZoneWithWorkNo(2, this.collectWorkingHours, this);
		workingHours2DispLay: KnockoutObservable<boolean> = ko.observable(false);
		workInformation: WorkInformation = new WorkInformation();
		workTypeList = ko.observableArray([]);
		workTypeSelected = new WorkTypeSelected();
		subWorkSubHolidayLinkingMngList: KnockoutObservableArray<SubWorkSubHolidayLinkingMng> = ko.observableArray([]);
		displayInforWhenStarting: any;
		//điều kiện hiển thị ※H1
		dk1: KnockoutObservable<boolean> = ko.observable(false);
		// điều kiện hiển thị ※E1
		dk2: KnockoutObservable<boolean> = ko.observable(false);
		// điều kiện hiển thị ※E1-1
		dk3: KnockoutObservable<boolean> = ko.observable(false);
		//
		dk4: KnockoutObservable<boolean> = ko.observable(false);
		constructor(appType: number){
			let self = this;
			self.appType = appType;
			self.workInformation.workType.subscribe((data: string)=>{
				if(data){
					self.workTypeSelected.update(_.find(self.workTypeList(), {'workTypeCode': data}));
					self.checkDisplay();
				}
			});
			self.workInformation.workTime.subscribe((data: string) =>{
				if(data){
					self.workTimeDisplay(data + " " + self.time_convert());	
				}
			});
		}
		
		update(param: any){
			let self = this;
			self.appID = param.appID;
			self.application.update(param.application);
			self.workInformation.update(param.workInformation);
			self.workingHours = param.workingHours;
			let e1 = _.find(param.workingHours, { 'workNo': 1});
			if(e1) {
				self.workingHours1.update(e1);	
			}
			let e2 = _.find(param.workingHours, { 'workNo': 2});
			if(e2) {
				self.workingHours2.update(e2);	
			}
			
		}
		
		bindDingScreenA(data: any, displayInforWhenStarting: any){
			let self = this;
			_.orderBy(data.workTypeList, ['code'], ['asc'])
			self.workTypeList(data.workTypeList);
			self.workingHours1.timeZone.update({startTime: data.startTime, endTime: data.endTime});
			if(data.startTime2 && data.endTime2){
				self.workingHours2DispLay(true);
				self.workingHours2.timeZone.update({startTime: data.startTime2, endTime: data.endTime2});	
			}
			
			self.workInformation.update(data);
			self.displayInforWhenStarting = displayInforWhenStarting;
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
			}
		}
		/**就業時間帯を反映する※1(Phản ánh worktime ※1) 勤務種類：1日の勤務区分//worktype: phân loại đi làm 1 ngày */
		checkE1Common(): boolean{
			let self = this;
			return self.workTypeSelected.workAtr == 1 && self.workTypeSelected.checkE11()
					&& (self.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 1 
						|| self.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 2) 
		}
				
		time_convert(): string{ 
			let self = this;
			let start = self.workingHours1.timeZone.startTime();
			let end = self.workingHours1.timeZone.endTime(); 
			if(!start || !end){
				return '';
			}
			return moment(Math.floor(start / 60),'mm').format('mm') + ":" + moment(start % 60,'mm').format('mm') + getText('KAF011_37') + moment(Math.floor(end / 60),'mm').format('mm') + ":" + moment(end % 60,'mm').format('mm');         
		}
		
		collectWorkingHours(self: any){
			self.workingHours = [];
			if(self.workingHours1.timeZone.startTime() && self.workingHours1.timeZone.endTime()){
				self.workingHours.push(self.workingHours1);
			}
			if(self.workingHours2.timeZone.startTime() && self.workingHours2.timeZone.endTime()){
				self.workingHours.push(self.workingHours2);
			}
		}
		
		openKDL003() {
			let self = this;
			nts.uk.ui.windows.setShared('parentCodes',{
										workTypeCodes: _.map(self.workTypeList(),'workTypeCode'),
										selectedWorkTypeCode: self.workInformation.workType(), 
										selectedWorkTimeCode: self.workInformation.workTime()
									});
			nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml').onClosed(() => {
				let data = nts.uk.ui.windows.getShared('childData');
					if(data){
						self.workingHours1.timeZone.startTime(data.first.start);
						self.workingHours1.timeZone.endTime(data.first.end);
						self.workInformation.workType(data.selectedWorkTypeCode);
						self.workInformation.workTime(data.selectedWorkTimeCode);
						if(data.second.start && data.second.end){
							self.workingHours2.timeZone.startTime(data.second.start);
							self.workingHours2.timeZone.endTime(data.second.end);
							self.workingHours2DispLay(true);	
						}
					}
			});
		}
		
		openKDL035() {
			let self = this;
			if(self.application.appDate() == "" ){
				if(self.appType == 0){
					$("#recAppDate").trigger("validate");	
				}else{
					$("#absAppDate").trigger("validate");	
				}
			}else{
				nts.uk.ui.windows.setShared('KDL035_PARAMS', {
					employeeId: self.application.employeeIDLst()[0],
					period: {
						startDate: moment(self.application.appDate()).format('YYYY/MM/DD'),
						endDate: moment(self.application.appDate()).format('YYYY/MM/DD')
					},
					daysUnit: 0.5,
					targetSelectionAtr: 1,
					actualContentDisplayList: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opActualContentDisplayLst,
					managementData: self.subWorkSubHolidayLinkingMngList()
				});
				nts.uk.ui.windows.sub.modal( '/view/kdl/035/a/index.xhtml').onClosed(() => {
					let data = nts.uk.ui.windows.getShared('KDL035_RESULT');
					if(data){
						self.subWorkSubHolidayLinkingMngList(data);
					}
				});
			}
		}
		
	}
	
	/** 振休申請 */	
	export class AbsenceLeaveApp extends RecruitmentApp {
		workChangeUse: KnockoutObservable<boolean> = ko.observable(false);
		changeSourceHoliday: KnockoutObservable<string> = ko.observable();
		
		constructor(appType: number){
			super(appType);
		}
		update(param: any){
			let self = this;
			super.update(param);
			self.workChangeUse(param.workChangeUse);
			self.changeSourceHoliday(param.changeSourceHoliday);
		}
	}
	
	export class Application extends ApplicationCommon{
		constructor(){
			super(AppType.COMPLEMENT_LEAVE_APPLICATION);
		}
		update(param: any){
			let self = this;
			self.prePostAtr(param.prePostAtr);
			self.appDate(param.appDate);
			self.opAppStandardReasonCD(param.opAppStandardReasonCD);
			self.opAppReason(param.opAppReason);
		}
	}
	
	export class WorkInformation {
		workType: KnockoutObservable<string> = ko.observable('');
		workTime: KnockoutObservable<string> = ko.observable('');
		constructor(){}
		update(param: any){
			let self = this;
			self.workType(param.workType);
			self.workTime(param.workTime);
		}
	}
	
	export class TimeZoneWithWorkNo {
		workNo: number;
		timeZone: TimeZone;
		constructor(workNo: number, collectWorkingHours: ((vm:any) => void), vm: any){
			let self = this;
			self.workNo = workNo;
			self.timeZone = new TimeZone(collectWorkingHours, vm)
		}
		update(param: any){
			let self = this;
			self.timeZone.update(param.timeZone);
		}
		
	}
	
	export class TimeZone {
		startTime: KnockoutObservable<number> = ko.observable();
		endTime: KnockoutObservable<number> = ko.observable();
		constructor(collectWorkingHours: ((vm: any) => void), vm: any){
			let self = this;
			self.startTime.subscribe(() => {
				collectWorkingHours(vm);
			});
			self.endTime.subscribe(() => {
				collectWorkingHours(vm);
			});
		}
		update(param: any){
			let self = this;
			self.startTime(param.startTime);
			self.endTime(param.endTime);
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
        employeeId: string;
        // 逐次休暇の紐付け情報 . 発生日
        outbreakDay: string;
        // 逐次休暇の紐付け情報 . 使用日
        dateOfUse: string;
        // 逐次休暇の紐付け情報 . 使用日数
        dayNumberUsed: number;
        // 逐次休暇の紐付け情報 . 対象選択区分
        targetSelectionAtr: number;
		constructor(param: any){
			let self = this;
			self.employeeId = param.employeeId;
			self.outbreakDay = param.outbreakDay;
			self.dateOfUse = param.dateOfUse;
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
		absApp: any;
		//振出申請
		recApp: any;
		constructor(){
			
		}
	}

}