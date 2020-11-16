 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.c.viewmodel {
	import DisplayWorkplace = nts.uk.at.view.kaf018.a.viewmodel.DisplayWorkplace;
	import ApprovalStatusMailType = kaf018.share.model.ApprovalStatusMailType;
	import ApprSttExecutionDto = nts.uk.at.view.kaf018.b.viewmodel.ApprSttExecutionDto;
	
	@bean()
	class Kaf018CViewModel extends ko.ViewModel {
		mailType: ApprovalStatusMailType = ApprovalStatusMailType.APP_APPROVAL_UNAPPROVED;
		name: string = '';
		description: string = '';
		items: KnockoutObservableArray<WkpEmpMailInfo> = ko.observableArray([
			new WkpEmpMailInfo('1', '1', 1, '基本給1'),
			new WkpEmpMailInfo('2', '2', 2, '基本給2'),
			new WkpEmpMailInfo('3', '3', 3, '基本給3'),
		]);
		currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
		mailSubject: KnockoutObservable<string> = ko.observable('');
		mailContent: KnockoutObservable<string> = ko.observable('');
		urlApprovalEmbed: KnockoutObservable<boolean> = ko.observable(false);
		urlDayEmbed: KnockoutObservable<boolean> = ko.observable(false);
		urlMonthEmbed: KnockoutObservable<boolean> = ko.observable(false);
		editMode: number = 0;
		displayUrlApprovalEmbed: boolean = false;
		displayUrlDayEmbed: boolean = false;
		displayUrlMonthEmbed: boolean = false;
		
		created(params: KAF018CParam) {
			const vm = this;
			vm.mailType = params.mailType;
			vm.displayUrlApprovalEmbed = vm.mailType==ApprovalStatusMailType.APP_APPROVAL_UNAPPROVED;
			vm.displayUrlDayEmbed = vm.mailType==ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL || 
									vm.mailType==ApprovalStatusMailType.DAILY_UNCONFIRM_BY_CONFIRMER ||
									vm.mailType==ApprovalStatusMailType.WORK_CONFIRMATION;
			vm.displayUrlMonthEmbed = vm.mailType==ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_PRINCIPAL || 
										vm.mailType==ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_CONFIRMER ||
										vm.mailType==ApprovalStatusMailType.WORK_CONFIRMATION;
			switch(vm.mailType) {
				case ApprovalStatusMailType.APP_APPROVAL_UNAPPROVED:
					vm.name = vm.$i18n('KAF018_491');
					vm.description = vm.$i18n('KAF018_497');
					break;
				case ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL:
					vm.name = vm.$i18n('KAF018_492');
					vm.description = vm.$i18n('KAF018_498');
					break;
				case ApprovalStatusMailType.DAILY_UNCONFIRM_BY_CONFIRMER:
					vm.name = vm.$i18n('KAF018_493');
					vm.description = vm.$i18n('KAF018_499');
					break;
				case ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_PRINCIPAL:
					vm.name = vm.$i18n('KAF018_494');
					vm.description = vm.$i18n('KAF018_500');
					break;
				case ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_CONFIRMER:
					vm.name = vm.$i18n('KAF018_495');
					vm.description = vm.$i18n('KAF018_501');
					break;
				case ApprovalStatusMailType.WORK_CONFIRMATION:
					vm.name = vm.$i18n('KAF018_496');
					vm.description = vm.$i18n('KAF018_502');
					break;
				default:
					break;
			}
			
			let mailType = vm.mailType,
				wsParam = { mailType };
			vm.$ajax('at', API.getEmpSendMailInfo, wsParam).then((data: any) => {
				vm.urlApprovalEmbed(data.approvalStatusMailTempDto.urlApprovalEmbed == 1 ? true: false);
				vm.urlDayEmbed(data.approvalStatusMailTempDto.urlDayEmbed == 1 ? true: false);
				vm.urlMonthEmbed(data.approvalStatusMailTempDto.urlMonthEmbed == 1 ? true: false);
				vm.mailSubject(data.approvalStatusMailTempDto.mailSubject);
				vm.mailContent(data.approvalStatusMailTempDto.mailContent);
				vm.editMode = data.approvalStatusMailTempDto.editMode;
			});
		}
		
		sendMail() {
			const vm = this;
			let command = vm.getMailTemplateParam(),
				wsParam = { command };
			vm.$ajax('at', API.sendMailToDestination, wsParam).then((data) => {
				if(data.OK) {
					
				} else {
					
				}
			}).fail((res) => {
				vm.$dialog.error({ messageId: res.messageId });
			});
		}
		
		close() {
			const vm = this;
			vm.$window.close();
		}
		
		updateMailTemplate() {
			const vm = this;
			let wsParam = vm.getMailTemplateParam();
			vm.$ajax('at', API.updateMailTemplate, [wsParam]).then(() => {
				vm.$dialog.info({ messageId: "Msg_1760" });
			});
		}
		
		getMailTemplateParam() {
			const vm = this;
			return {
				mailType: vm.mailType,
				urlApprovalEmbed: vm.urlApprovalEmbed() ? 1 : 0,
				urlDayEmbed: vm.urlDayEmbed() ? 1 : 0,
				urlMonthEmbed: vm.urlMonthEmbed() ? 1 : 0,
				mailSubject: vm.mailSubject(),
				mailContent: vm.mailContent(),
				editMode: vm.editMode
			};
		}
	}
	
	export interface KAF018CParam {
		mailType: ApprovalStatusMailType;
		selectWorkplaceInfo: Array<DisplayWorkplace>;
	}
	
	export class WkpEmpMailInfo {
		wkpID: string;
		wkpName: string;	
		numberPeople: number;
		mailInfo: string;
		constructor(wkpID: string, wkpName: string, numberPeople: number, mailInfo: string) {
			this.wkpID = wkpID;
			this.wkpName = wkpName;
			this.numberPeople = numberPeople;
			this.mailInfo = mailInfo;
		}
	}

	const API = {
		getEmpSendMailInfo: "at/request/application/approvalstatus/getEmpSendMailInfo",
		updateMailTemplate: "at/request/application/approvalstatus/registerMail",
		sendMailToDestination: "at/request/application/approvalstatus/sendMailToDestination"
	}
}