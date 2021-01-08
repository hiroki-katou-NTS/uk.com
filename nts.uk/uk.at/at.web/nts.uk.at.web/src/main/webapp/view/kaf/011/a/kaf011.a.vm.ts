/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf011.a.viewmodel {
	
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Application = nts.uk.at.view.kaf011.Application;
	import RecruitmentApp = nts.uk.at.view.kaf011.RecruitmentApp;
	import AbsenceLeaveApp = nts.uk.at.view.kaf011.AbsenceLeaveApp;
	import Comment = nts.uk.at.view.kaf011.Comment;

	@bean()
	export class Kaf011AViewModel extends Kaf000AViewModel {
		params: AppInitParam;
		
		appType: KnockoutObservable<number> = ko.observable(AppType.COMPLEMENT_LEAVE_APPLICATION);
		applicationCommon: KnockoutObservable<Application> = ko.observable(new Application());
		
		displayInforWhenStarting = ko.observable(null);
		
		isSendMail = ko.observable(false);
		remainDays = ko.observable('');
		comment = new Comment();
		workTypeListWorkingDay = ko.observableArray([]);
		workTypeListHoliDay = ko.observableArray([]);
		
		appCombinaSelected = ko.observable(0);
		appCombinaDipslay = ko.observable(false);
		
		recruitmentApp = new RecruitmentApp(0);
		absenceLeaveApp = new AbsenceLeaveApp(1);
		
		isAgentMode = ko.observable(false);
		selectedRuleCode = ko.observable('');
		required = ko.observable(true);
		enable = ko.observable(true);
		date = ko.observable('2020/10/20');
		
		workTypeSelected = ko.observable(1);
		workTime = ko.observable('001 aaaa 08:30 ~ 12:00');
		
		startTime1 = ko.observable(510);
		endTime1 = ko.observable(1050);
		
		related = [
			{date: '2020/04/04(土)', number: '0.5日'},
			{date:  '2020/04/04(土)', number: '0.5日'}
		];
		
		settingCheck = ko.observable(true);
		isFromOther: boolean = false;
		
		created(params: AppInitParam) {
			const vm = this;
			vm.params = params;
			if(!_.isNil(__viewContext.transferred.value)) {
				vm.isFromOther = true;
			}
			sessionStorage.removeItem('nts.uk.request.STORAGE_KEY_TRANSFER_DATA');
			let empLst: Array<string> = [];
			let	dateLst: Array<string> = [];
			if(params){
				empLst = params.employeeIds || [__viewContext.user.employeeId];
				if (!_.isEmpty(params.baseDate)) {
					let paramDate = moment(params.baseDate).format('YYYY/MM/DD');
					dateLst = [paramDate];
					
				}
				if (params.isAgentMode) {
					vm.isAgentMode(params.isAgentMode);
				}
			}
			vm.$blockui("grayout");
			vm.loadData(empLst, dateLst, vm.appType()).then(() => {
				vm.$blockui("grayout");
				vm.$ajax('at/request/application/holidayshipment/startPageARefactor',{sIDs: [], appDate: [], appDispInfoStartup: vm.appDispInfoStartupOutput()}).then((data: any) =>{
					vm.displayInforWhenStarting(data);
					vm.isSendMail(data.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.manualSendMailAtr == 1);
					vm.remainDays(data.remainingHolidayInfor.remainDays + '日');
					vm.workTypeListWorkingDay(data.applicationForWorkingDay.workTypeList);
					vm.workTypeListHoliDay(data.applicationForHoliday.workTypeList);
					vm.appCombinaDipslay(data.substituteHdWorkAppSet.simultaneousApplyRequired == 1);
					vm.recruitmentApp.bindingScreenA(data.applicationForWorkingDay, data);
					vm.absenceLeaveApp.bindingScreenA(data.applicationForHoliday, data);
					vm.comment.update(data.substituteHdWorkAppSet);
					vm.recruitmentApp.application.appDate(dateLst[0]);
					vm.recruitmentApp.application.opAppStartDate(dateLst[0]);
                    vm.recruitmentApp.application.opAppEndDate(dateLst[0]);
					$('#isSendMail').css({'display': 'inline-block'});
					$('#contents-area').css({'display': ''});
					vm.$blockui("hide"); 
				}).fail((failData: any) => {
					$('#functions-area').css({'display': 'none'});
					vm.$dialog.error(failData).then(() => { vm.$jump("com", "/view/ccg/008/a/index.xhtml"); });
				}).always(() => {
					
				});
			}).fail((failData: any) => {
				console.log(failData);
				if (failData.messageId === "Msg_43") {
					vm.$dialog.error(failData).then(() => { vm.$jump("com", "/view/ccg/008/a/index.xhtml"); });
				} else {
					vm.$dialog.error(failData);
				}
				vm.$blockui("hide"); 
			});
			
			// refer Object common to rec and abs
			vm.absenceLeaveApp.application.prePostAtr = vm.recruitmentApp.application.prePostAtr = vm.applicationCommon().prePostAtr;
			vm.absenceLeaveApp.application.employeeIDLst = vm.recruitmentApp.application.employeeIDLst = vm.applicationCommon().employeeIDLst;
			vm.absenceLeaveApp.application.opAppStandardReasonCD = vm.recruitmentApp.application.opAppStandardReasonCD = vm.applicationCommon().opAppStandardReasonCD;
			vm.absenceLeaveApp.application.opAppReason = vm.recruitmentApp.application.opAppReason = vm.applicationCommon().opAppReason;
			
			vm.recruitmentApp.application.appDate.subscribe(value =>{
				if(value != "" && !$('#recAppDate').ntsError('hasError')){
					vm.$blockui("grayout");
					let holidayDate = (vm.appCombinaSelected() != 1 && vm.absenceLeaveApp.application.appDate() && !$('#absAppDate').ntsError('hasError')) ? moment(vm.absenceLeaveApp.application.appDate()).format('YYYY/MM/DD'): null;
					vm.$ajax('at/request/application/holidayshipment/changeRecDate',{workingDate: moment(value).format('YYYY/MM/DD'), holidayDate: holidayDate, displayInforWhenStarting: vm.displayInforWhenStarting()}).then((data: any) =>{
						vm.bindData(data);
					}).always(() => {
						vm.$blockui("hide"); 
					});
				}
			});
			
			vm.absenceLeaveApp.application.appDate.subscribe(value =>{
				if(value != "" && !$('#absAppDate').ntsError('hasError')){
					vm.$blockui("grayout");
					let workingDate = (vm.appCombinaSelected() != 2 && vm.recruitmentApp.application.appDate() && !$('#recAppDate').ntsError('hasError')) ? moment(vm.recruitmentApp.application.appDate()).format('YYYY/MM/DD'): null;
					vm.$ajax('at/request/application/holidayshipment/changeAbsDate',{workingDate: workingDate, holidayDate: moment(value).format('YYYY/MM/DD'), displayInforWhenStarting: vm.displayInforWhenStarting()}).then((data: any) =>{
						vm.bindData(data);
					}).always(() => {
						vm.$blockui("hide"); 
					});
				}
			});
			
			vm.appCombinaSelected.subscribe(value => {
				nts.uk.ui.errors.clearAll();
				if(value == 0 || value == 1){
					vm.recruitmentApp.application.appDate.valueHasMutated();
				}else{
					vm.absenceLeaveApp.application.appDate.valueHasMutated();
				}
			});
		}
		
		bindData(data:any){
			let vm =this;
			vm.appDispInfoStartupOutput(data.appDispInfoStartup);
			vm.displayInforWhenStarting(data);
			vm.isSendMail(data.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.manualSendMailAtr == 1);
			vm.remainDays(data.remainingHolidayInfor.remainDays + '日');
			vm.workTypeListWorkingDay(data.applicationForWorkingDay.workTypeList);
			vm.workTypeListHoliDay(data.applicationForHoliday.workTypeList);
			vm.appCombinaDipslay(data.substituteHdWorkAppSet.simultaneousApplyRequired == 1);
			vm.recruitmentApp.bindingScreenA(data.applicationForWorkingDay, data);
			vm.absenceLeaveApp.bindingScreenA(data.applicationForHoliday, data);
			vm.comment.update(data.substituteHdWorkAppSet);
		}
		
		mounted(){
			
		}
		
		triggerValidate(): boolean{
			$('.nts-input').trigger("validate");
			$('input').trigger("validate");
			return nts.uk.ui.errors.hasError();
		}
		
		register() {
			const vm = this;
			if(!vm.triggerValidate()){
				let self = this;
				let data = self.displayInforWhenStarting();
					data.rec = self.appCombinaSelected() != 2 ? ko.toJS(self.recruitmentApp): null;
					if(data.rec){
						data.rec.application.opAppStartDate = data.rec.application.opAppEndDate = data.rec.application.appDate = moment(data.rec.application.appDate).format('YYYY/MM/DD');
						_.remove(data.rec.workingHours, function(n: any) {
							return n.timeZone.startTime == undefined || n.timeZone.startTime == undefined;  
						}); 
					}
					data.abs = self.appCombinaSelected() != 1 ? ko.toJS(self.absenceLeaveApp): null;
					if(data.abs){
						data.abs.application.opAppStartDate = data.abs.application.opAppEndDate = data.abs.application.appDate = moment(data.abs.application.appDate).format('YYYY/MM/DD');
						_.remove(data.abs.workingHours, function(n: any) {
							return n.timeZone.startTime == undefined || n.timeZone.startTime == undefined;  
						}); 
					}
				console.log(data);	
				vm.$ajax('at/request/application/holidayshipment/save', data).then((data: any) =>{
					vm.$dialog.info({ messageId: "Msg_15" });
				}).fail((failData) => {
					vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds });
				});
			}
			
			
		}
		
		openKDL009() {
			let self = this;
			nts.uk.ui.windows.setShared('KDL009_DATA', {
				employeeIds: (self.params ? self.params.employeeIds : [__viewContext.user.employeeId]),
				baseDate: moment(new Date()).format("YYYYMMDD")
			});
			if(self.params && self.params.employeeIds.length > 1){
				nts.uk.ui.windows.sub.modal( '/view/kdl/009/a/multi.xhtml');	
			}else{
				nts.uk.ui.windows.sub.modal( '/view/kdl/009/a/single.xhtml');
			}
			
		}
		
	}
	export interface KAF011Param {
		employeeIDLst: string[];
		recAppDate: string; 
		absAppDate: string;
		
	}
	

}