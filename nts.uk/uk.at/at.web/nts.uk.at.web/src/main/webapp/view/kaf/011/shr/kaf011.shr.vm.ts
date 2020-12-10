module nts.uk.at.view.kaf011 {
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import ApplicationCommon = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	
	/** 振出申請 */
	export class RecruitmentApp {
		appID: string;
		application: Application = new Application();
		workInformation: WorkInformation = new WorkInformation();
		workingHours: TimeZoneWithWorkNo[] = [];
		workingHoursDispLay1: TimeZoneWithWorkNo = new TimeZoneWithWorkNo(1, this.collectWorkingHours);
		workingHoursDispLay2: TimeZoneWithWorkNo = new TimeZoneWithWorkNo(2, this.collectWorkingHours);
		constructor(){}
		update(param: any){
			let self = this;
			self.appID = param.appID;
			self.application.update(param.application);
			self.workInformation.update(param.workInformation);
			self.workingHours = param.workingHours;
			let e1 = _.find(param.workingHours, { 'workNo': 1});
			if(e1) {
				self.workingHoursDispLay1.update(e1);	
			}
			let e2 = _.find(param.workingHours, { 'workNo': 2});
			if(e2) {
				self.workingHoursDispLay2.update(e2);	
			}
			
		}
		collectWorkingHours(){
			let self = this;
			self.workingHours = [];
			if(self.workingHoursDispLay1.timeZone.startTime() && self.workingHoursDispLay1.timeZone.endTime()){
				self.workingHours.push(self.workingHoursDispLay1);
			}
			if(self.workingHoursDispLay2.timeZone.startTime() && self.workingHoursDispLay2.timeZone.endTime()){
				self.workingHours.push(self.workingHoursDispLay2);
			}
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
		constructor(workNo: number, collectWorkingHours: (() => void)){
			let self = this;
			self.workNo = workNo;
			self.timeZone = new TimeZone(collectWorkingHours)
		}
		update(param: any){
			let self = this;
			self.timeZone.update(param.timeZone);
		}
	}
	
	export class TimeZone {
		startTime: KnockoutObservable<number> = ko.observable();
		endTime: KnockoutObservable<number> = ko.observable();
		constructor(collectWorkingHours: (() => void)){
			let self = this;
			self.startTime.subscribe(() => {
				collectWorkingHours();
			});
			self.endTime.subscribe(() => {
				collectWorkingHours();
			});
		}
		update(param: any){
			let self = this;
			self.startTime(param.startTime);
			self.endTime(param.endTime);
		}
	}

}