/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf011.a.viewmodel {
	
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import DisplayInforWhenStarting = nts.uk.at.view.kaf011.DisplayInforWhenStarting;
	
	const APIKAF011 = {
        start: "at/request/application/holidayshipment/startPageARefactor"
    }

	@bean()
	export class Kaf011AViewModel extends Kaf000AViewModel {
		appType: KnockoutObservable<number> = ko.observable(AppType.COMPLEMENT_LEAVE_APPLICATION);
		application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
		
		displayInforWhenStarting: DisplayInforWhenStarting;
		
		isSendMail = ko.observable(false);
		workTypeListWorkingDay = ko.observableArray([]);
		workTypeListHoliDay = ko.observableArray([]);
		
		appCombinaSelected = ko.observable(0);
		appCombinaDipslay = ko.observable(false);
		
		
		
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
		comment = ko.observable('123456789');
		
		settingCheck = ko.observable(true);
		
		created() {
			const vm = this;
			vm.LoadSetting();
			let command = {sIDs: [], appDate: []};
			vm.$ajax(APIKAF011.start, command).then((data: DisplayInforWhenStarting)=>{
				vm.displayInforWhenStarting = data;
				vm.isSendMail(data.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.manualSendMailAtr == 1);
				vm.workTypeListWorkingDay(data.applicationForWorkingDay.workTypeList);
				vm.workTypeListHoliDay(data.applicationForHoliday.workTypeList);
				vm.appCombinaDipslay(data.substituteHdWorkAppSet.simultaneousApplyRequired == 0);
			});
			
		}
		
		LoadSetting(){
			const vm = this;
			let empLst: Array<string> = [];
			let	dateLst: Array<string> = [];
			vm.$blockui("grayout");
			vm.loadData(empLst, dateLst, vm.appType()).then((loadDataFlag: any) => {
			
			}).fail((failData: any) => {
				console.log(failData);
				if (failData.messageId === "Msg_43") {
					vm.$dialog.error(failData).then(() => { vm.$jump("com", "/view/ccg/008/a/index.xhtml"); });

				} else {
					vm.$dialog.error(failData);
				}
			}).always(() => {vm.$blockui("hide"); });
		}
		
		mounted() {
			const vm = this;
		}
		
		register() {
			
		}
		
		
	}

}