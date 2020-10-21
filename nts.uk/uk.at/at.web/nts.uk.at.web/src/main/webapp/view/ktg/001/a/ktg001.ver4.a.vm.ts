module nts.uk.at.view.ktg001.a {

	import windows = nts.uk.ui.windows;

	export const KTG001_API = {
		GET_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/display',
		UPDATE_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/setting',
	};

	export enum NotUseAtr {
		NOT_USE = 0,
		USE = 1
	}

	export enum ApprovedApplicationStatusItem {
		APPLICATION_DATA = 0,
		DAILY_PERFORMANCE_DATA = 1,
		MONTHLY_RESULT_DATA = 2,
		AGREEMENT_APPLICATION_DATA = 3
	}

	export interface IParam {
		ym: KnockoutObservable<number>;
		closureId: KnockoutObservable<number>;
	}

	export interface IApprovedDataExecutionResult {
		haveParticipant: Boolean;
		topPagePartName: string;
		appDisplayAtr: Boolean;
		dayDisplayAtr: Boolean;
		monthDisplayAtr: Boolean;
		aggrDisplayAtr: Boolean;
		approvedAppStatusDetailedSettings: Array<IApprovedAppStatusDetailedSetting>;
		closingPeriods: Array<IClosureIdPresentClosingPeriod>;
	}

	export interface IApprovedAppStatusDetailedSetting {
		displayType: number;
		item: number;
	}

	export interface IClosureIdPresentClosingPeriod {
		closureId: number;
		currentClosingPeriod: IPresentClosingPeriodImport;
	}

	export interface IPresentClosingPeriodImport {
		processingYm: number;
		closureStartDate: String;
		closureEndDate: String;
	}

	export interface IResponse {
		approvedDataExecutionResultDto: IApprovedDataExecutionResult;
		approvalProcessingUseSetting: IApprovalProcessingUseSetting;
	}

	export interface IApprovalProcessingUseSetting {
		useDayApproverConfirm: Boolean;
		useMonthApproverConfirm: Boolean;
	}


	@bean()
	class ViewModel extends ko.ViewModel {

		title: KnockoutObservable<string> = ko.observable('');
		appText: KnockoutObservable<string> = ko.observable('');
		dayText: KnockoutObservable<string> = ko.observable('');
		monText: KnockoutObservable<string> = ko.observable('');
		aggrText: KnockoutObservable<string> = ko.observable('');
		selectedSwitch: KnockoutObservable<number> = ko.observable(1);

		appRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
		dayRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
		monRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
		aggrRowVisible: KnockoutObservable<Boolean> = ko.observable(false);

		appIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
		dayIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
		monIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
		aggrIconVisible: KnockoutObservable<Boolean> = ko.observable(false);

		created() {

		}

		mounted() {
			let vm = this;
			let cacheCcg008 = windows.getShared("cache");
			let closureId = 1;

			let param = {
				ym: vm.selectedSwitch(),
				closureId: closureId

			};

			if (!cacheCcg008 || !cacheCcg008.currentOrNextMonth) {
				vm.selectedSwitch(1);
			} else {
				vm.selectedSwitch(cacheCcg008.currentOrNextMonth);
				closureId = cacheCcg008.closureId;
			}

			vm.$ajax(KTG001_API.GET_APPROVED_DATA_EXCECUTION, param).done((data: IResponse) => {
				if (data) {
					let approvedDataExecution = data.approvedDataExecutionResultDto;
					let approvalProcessingUse = data.approvalProcessingUseSetting;

					vm.title(approvedDataExecution.topPagePartName);

					approvedDataExecution.approvedAppStatusDetailedSettings.forEach(i => {
						if (i.item == ApprovedApplicationStatusItem.APPLICATION_DATA && i.displayType == NotUseAtr.USE) {
							vm.appRowVisible(true);
							vm.appText(approvedDataExecution.appDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
							vm.appIconVisible(approvedDataExecution.appDisplayAtr);
						}

						if (i.item == ApprovedApplicationStatusItem.DAILY_PERFORMANCE_DATA && i.displayType == NotUseAtr.USE && approvalProcessingUse.useDayApproverConfirm == true) {
							vm.dayRowVisible(true);
							vm.dayText(approvedDataExecution.dayDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
							vm.dayIconVisible(approvedDataExecution.dayDisplayAtr);
						}

						if (i.item == ApprovedApplicationStatusItem.MONTHLY_RESULT_DATA && i.displayType == NotUseAtr.USE && approvalProcessingUse.useMonthApproverConfirm == true) {
							vm.monRowVisible(true);
							vm.monText(approvedDataExecution.monthDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
							vm.monIconVisible(approvedDataExecution.monthDisplayAtr);
						}

						if (i.item == ApprovedApplicationStatusItem.AGREEMENT_APPLICATION_DATA && i.displayType == NotUseAtr.USE) {
							vm.aggrRowVisible(true);
							vm.aggrText(approvedDataExecution.aggrDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
							vm.aggrIconVisible(approvedDataExecution.aggrDisplayAtr);
						}

					})
				}

			}).always(() => vm.$blockui("clear"));

		}

		applicationList() {
			windows.top.location = windows.location.origin + '/nts.uk.at.web/view/cmm/045/a/index.xhtml';
		}

		dayPerformanceConfirm() {
			windows.top.location = windows.location.origin + '/nts.uk.at.web/view/kdw/004/a/index.xhtml';
		}

		monPerformanceConfirm() {
			windows.top.location = windows.location.origin + '/nts.uk.at.web/view/kmw/003/a/index.xhtml';
		}

		aggrementApproval() {
		}

		
		setting() {
			//windows.top.location = windows.location.origin + '/nts.uk.at.web/view/ktg/001/b/index_ver4.xhtml';
			nts.uk.ui.windows.sub.modal('at', '/view/ktg/001/b/index_ver4.xhtml');
		}



	}
}