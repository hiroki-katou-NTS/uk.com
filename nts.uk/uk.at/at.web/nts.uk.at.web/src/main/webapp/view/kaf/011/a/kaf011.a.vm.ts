/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf011.a.viewmodel {
	
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Application = nts.uk.at.view.kaf011.Application;
	import RecruitmentApp = nts.uk.at.view.kaf011.RecruitmentApp;
	import AbsenceLeaveApp = nts.uk.at.view.kaf011.AbsenceLeaveApp;
	import Comment = nts.uk.at.view.kaf011.Comment;
	const APIKAF011 = {
        start: "at/request/application/holidayshipment/startPageARefactor"
    }

	@bean()
	export class Kaf011AViewModel extends Kaf000AViewModel {
		appType: KnockoutObservable<number> = ko.observable(AppType.COMPLEMENT_LEAVE_APPLICATION);
		applicationCommon: KnockoutObservable<Application> = ko.observable(new Application());
		
		displayInforWhenStarting: any;
		
		isSendMail = ko.observable(false);
		remainDays = ko.observable('');
		comment = new Comment();
		workTypeListWorkingDay = ko.observableArray([]);
		workTypeListHoliDay = ko.observableArray([]);
		
		appCombinaSelected = ko.observable(0);
		appCombinaDipslay = ko.observable(false);
		
		recruitmentApp = new RecruitmentApp();
		absenceLeaveApp = new AbsenceLeaveApp();
		
		
		
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
		
		created() {
			const vm = this;
			let empLst: Array<string> = [];
			let	dateLst: Array<string> = [];
			vm.$blockui("grayout");
			vm.loadData(empLst, dateLst, vm.appType()).then(() => {
				vm.$ajax(APIKAF011.start, {sIDs: [], appDate: [], appDispInfoStartup: vm.appDispInfoStartupOutput()}).then((data: any) =>{
					vm.displayInforWhenStarting = data;
					vm.isSendMail(data.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.manualSendMailAtr == 1);
					vm.remainDays(data.remainingHolidayInfor.remainDays + '日');
					vm.workTypeListWorkingDay(data.applicationForWorkingDay.workTypeList);
					vm.workTypeListHoliDay(data.applicationForHoliday.workTypeList);
					vm.appCombinaDipslay(data.substituteHdWorkAppSet.simultaneousApplyRequired == 0);
					vm.recruitmentApp.workInformation.update(data.applicationForWorkingDay);
					vm.absenceLeaveApp.workInformation.update(data.applicationForHoliday);
					vm.comment.update(data.substituteHdWorkAppSet);
				}).always(() => {
					$('#functions-area').css({'display': ''});
					$('#contents-area').css({'display': ''});
					vm.$blockui("hide"); 
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
			
			vm.absenceLeaveApp.application.prePostAtr = vm.recruitmentApp.application.prePostAtr = vm.applicationCommon().prePostAtr;
			vm.absenceLeaveApp.application.employeeIDLst = vm.recruitmentApp.application.employeeIDLst = vm.applicationCommon().employeeIDLst;
			vm.absenceLeaveApp.application.opAppStandardReasonCD = vm.recruitmentApp.application.opAppStandardReasonCD = vm.applicationCommon().opAppStandardReasonCD;
			vm.absenceLeaveApp.application.opAppReason = vm.recruitmentApp.application.opAppReason = vm.applicationCommon().opAppReason;
		}
		mounted(){}
		
		register() {
			
		}
		
		
	}

}