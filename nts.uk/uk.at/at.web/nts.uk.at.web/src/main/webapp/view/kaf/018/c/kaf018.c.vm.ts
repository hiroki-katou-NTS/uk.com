 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.c.viewmodel {
	import DisplayWorkplace = nts.uk.at.view.kaf018.a.viewmodel.DisplayWorkplace;
	import ApprovalStatusMailType = kaf018.share.model.ApprovalStatusMailType;
	import ApprSttExecutionDto = nts.uk.at.view.kaf018.b.viewmodel.ApprSttExecutionDto;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;
	
	@bean()
	class Kaf018CViewModel extends ko.ViewModel {
		mailType: ApprovalStatusMailType = ApprovalStatusMailType.APP_APPROVAL_UNAPPROVED;
		name: string = '';
		description: string = '';
		dataSource: Array<ApprSttWkpEmpMailOutput> = [];
		mailSubject: KnockoutObservable<string> = ko.observable('');
		mailContent: KnockoutObservable<string> = ko.observable('');
		urlApprovalEmbed: KnockoutObservable<boolean> = ko.observable(false);
		urlDayEmbed: KnockoutObservable<boolean> = ko.observable(false);
		urlMonthEmbed: KnockoutObservable<boolean> = ko.observable(false);
		editMode: number = 0;
		displayUrlApprovalEmbed: boolean = false;
		displayUrlDayEmbed: boolean = false;
		displayUrlMonthEmbed: boolean = false;
		displayUpdTmpBtn: boolean = false;
		
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
			vm.displayUpdTmpBtn = __viewContext.user.role.isInCharge.attendance;
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
				closureId = params.closureItem.closureId,
				processingYm = params.closureItem.processingYm,
				closureDay = params.closureItem.closureDay,
				lastDayOfMonth = params.closureItem.lastDayOfMonth,
				startDate = params.startDate,
				endDate = params.endDate,
				wkpInfoLst = params.selectWorkplaceInfo,
				employmentCDLst = params.employmentCDLst,
				wsParam = { mailType, closureId, processingYm, closureDay, lastDayOfMonth, startDate, endDate, wkpInfoLst, employmentCDLst };
			vm.$blockui('show');	
			vm.$ajax('at', API.getEmpSendMailInfo, wsParam).then((data: any) => {
				vm.urlApprovalEmbed(data.approvalStatusMailTempDto.urlApprovalEmbed == 1 ? true: false);
				vm.urlDayEmbed(data.approvalStatusMailTempDto.urlDayEmbed == 1 ? true: false);
				vm.urlMonthEmbed(data.approvalStatusMailTempDto.urlMonthEmbed == 1 ? true: false);
				vm.mailSubject(data.approvalStatusMailTempDto.mailSubject);
				vm.mailContent(data.approvalStatusMailTempDto.mailContent);
				vm.editMode = data.approvalStatusMailTempDto.editMode;
				vm.dataSource = _.sortBy(data.wkpEmpMailLst, 'hierarchyCode');
				_.forEach(vm.dataSource, item => {
					_.set(item, 'flag', false);	
				});
				vm.createGrid();
			}).then(() => {
				$('.kaf018-c-column-checkBox').find("span").click();
			}).fail((error) => {
				vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds })
				.then(() => vm.$window.close());
			});
		}
		
		createGrid() {
			const vm = this;
			$("#cGrid").ntsGrid({
				width: 860,
				height: 255,
				dataSource: vm.dataSource,
				primaryKey: 'wkpID',
				rowVirtualization: true,
				virtualization: true,
				virtualizationMode: 'continuous',
				dataRendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
						$(".ui-iggrid").focus();
					});
				},
				rendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
					});
			    },
				columns: [
					{ headerText: '', key: 'wkpID', dataType: 'string', width: '60px', hidden: true },
					{ 
						headerText: '', 
						key: 'flag', 
						dataType: 'boolean', 
						width: '60px', 
						showHeaderCheckbox: true, 
						ntsControl: 'Checkbox',
						columnCssClass: 'kaf018-c-column-checkBox' 
					},
					{ 
						headerText: vm.$i18n('KAF018_350'), 
						key: 'wkpName',
						width: '200px', 
						dataType: 'string',
						
					},
					{ 
						headerText: vm.$i18n('KAF018_550'), 
						key: 'countEmp', 
						width: '100px', 
						columnCssClass: 'kaf018-c-column-countEmp',
						dataType: 'number', 
						formatter: (key: number) => key + vm.$i18n('KAF018_551')
					},
					{ 
						headerText: vm.$i18n('KAF018_351') + vm.$i18n('KAF018_352'), 
						key: 'wkpID', 
						width: '500px', 
						formatter: (key: string) => vm.displayEmpMail(key)
					},
				],
				features: [
					{
						name: 'MultiColumnHeaders'
					}
				],
				ntsControls: [
					{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true }
				]
			});	
		}
		
		displayEmpMail(value: string) {
			const vm = this;
			let empMailLst = _.find(vm.dataSource, o => o.wkpID == value).empMailLst;
			if(_.isEmpty(empMailLst)) {
				return '';	
			}
			return _.chain(empMailLst).map(o => o.empName + (o.empMail ? vm.$i18n('KAF018_503') : '')).join(vm.$i18n('KAF018_504')).value();
		}
		
		sendMail() {
			const vm = this;
			let command = vm.getMailTemplateParam(),
				wkpEmpMailLst = _.filter(vm.dataSource, 'flag'),
				screenUrlApprovalEmbed = vm.urlApprovalEmbed(),
				screenUrlDayEmbed = vm.urlDayEmbed(),
				screenUrlMonthEmbed = vm.urlMonthEmbed(),
				wsParam = { command, wkpEmpMailLst, screenUrlApprovalEmbed, screenUrlDayEmbed, screenUrlMonthEmbed };
			if(_.isEmpty(wkpEmpMailLst)) {
				vm.$dialog.info({ messageId: "Msg_786" });
				return;
			}
			vm.$blockui('show');
			vm.$ajax('at', API.sendMailToDestination, wsParam).then((data) => {
				if(data.ok) {
					vm.$dialog.info({ messageId: "Msg_792" });	
				} else {
					vm.$dialog.error(vm.$i18n.message("Msg_793") + "<br/>" + _.join(data.listError,', '));	
				}
			}).fail((res) => {
				vm.$dialog.error({ messageId: res.messageId });
			}).always(() => vm.$blockui('hide'));
		}
		
		close() {
			const vm = this;
			vm.$window.close();
		}
		
		updateMailTemplate() {
			const vm = this;
			let wsParam = vm.getMailTemplateParam();
			vm.$blockui('show');
			vm.$ajax('at', API.updateMailTemplate, [wsParam]).then(() => {
				vm.$dialog.info({ messageId: "Msg_1760" });
			}).always(() => vm.$blockui('hide'));
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
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		selectWorkplaceInfo: Array<DisplayWorkplace>;
		employmentCDLst: Array<string>;
	}
	
	interface ApprSttWkpEmpMailOutput {
		wkpID: string;
		wkpCD: string;
		wkpName: string;
		hierarchyCode: string;
		countEmp: number;
		empMailLst: Array<ApprSttEmpMailOutput>;
		flag: boolean;
	}
	
	interface ApprSttEmpMailOutput {
		empID: string;
		empName: string;
		empMail: string;
	}

	const API = {
		getEmpSendMailInfo: "at/request/application/approvalstatus/getEmpSendMailInfo",
		updateMailTemplate: "at/request/application/approvalstatus/registerMail",
		sendMailToDestination: "at/request/application/approvalstatus/sendMailToDestination"
	}
}