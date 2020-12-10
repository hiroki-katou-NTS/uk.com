module nts.uk.at.view.kaf011 {
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import ApplicationCommon = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import getText = nts.uk.resource.getText;
	/** 振出申請 */
	export class RecruitmentApp {
		appID: string;
		application: Application = new Application();
		workingHours: TimeZoneWithWorkNo[] = [];
		workTimeDisplay: KnockoutObservable<string> = ko.observable('');
		workingHours1: TimeZoneWithWorkNo = new TimeZoneWithWorkNo(1, this.collectWorkingHours, this);
		workingHours2: TimeZoneWithWorkNo = new TimeZoneWithWorkNo(2, this.collectWorkingHours, this);
		workingHours2DispLay: KnockoutObservable<boolean> = ko.observable(false);
		workInformation: WorkInformation = new WorkInformation();
		workTypeList = ko.observableArray([]);
		
		constructor(){
			let self = this;
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
		
		bindDingScreenA(data: any){
			let self = this;
			self.workTypeList(data.workTypeList);
			self.workingHours1.timeZone.update({startTime: data.startTime, endTime: data.endTime});
			if(data.startTime2 && data.endTime2){
				self.workingHours2DispLay(true);
				self.workingHours2.timeZone.update({startTime: data.startTime2, endTime: data.endTime2});	
			}
			
			self.workInformation.update(data);
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
			nts.uk.ui.windows.setShared('parentCodes', {selectedWorkTypeCode: self.workInformation.workType(), selectedWorkTimeCode: self.workInformation.workTime()});
			nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml');
		}
		
	}
	
	/** 振休申請 */	
	export class AbsenceLeaveApp extends RecruitmentApp {
		workChangeUse: KnockoutObservable<boolean> = ko.observable(false);
		changeSourceHoliday: KnockoutObservable<string> = ko.observable();
		constructor(){
			super();
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

}