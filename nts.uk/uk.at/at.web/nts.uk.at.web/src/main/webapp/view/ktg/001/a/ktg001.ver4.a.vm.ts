module nts.uk.at.view.ktg001 {

	import windows = nts.uk.ui.windows;

	const KTG001_API = {
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

	interface IResponse {
		approvedDataExecutionResultDto: IApprovedDataExecutionResult;
		approvalProcessingUseSetting: IApprovalProcessingUseSetting;
	}

	interface IApprovalProcessingUseSetting {
		useDayApproverConfirm: Boolean;
		useMonthApproverConfirm: Boolean;
	}


	@bean()
	class ViewModel extends ko.ViewModel {

		title: KnockoutObservable<string> = ko.observable('');
		appText: KnockoutObservable<string> = ko.observable('あり');
		dayText: KnockoutObservable<string> = ko.observable('あり');
		monText: KnockoutObservable<string> = ko.observable('あり');
		aggrText: KnockoutObservable<string> = ko.observable('あり');
		selectedSwitch: KnockoutObservable<number> = ko.observable(1);
		param: KnockoutObservable<IParam> = ko.observable(null);
		visible: KnockoutObservable<Boolean> = ko.observable(false);

		created() {

		}
		
		setting(){}

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
							vm.appText = approvedDataExecution.appDisplayAtr == true ? ko.observable(vm.$i18n('KTG001_5')) : ko.observable(vm.$i18n('KTG001_6'));
							vm.visible(approvedDataExecution.appDisplayAtr);
						}

						if (i.item == ApprovedApplicationStatusItem.DAILY_PERFORMANCE_DATA && i.displayType == NotUseAtr.USE && approvalProcessingUse.useDayApproverConfirm == true) {
							vm.dayText = approvedDataExecution.dayDisplayAtr == true ? ko.observable(vm.$i18n('KTG001_5')) : ko.observable(vm.$i18n('KTG001_6'));
							vm.visible(approvedDataExecution.dayDisplayAtr);
						}

						if (i.item == ApprovedApplicationStatusItem.MONTHLY_RESULT_DATA && i.displayType == NotUseAtr.USE && approvalProcessingUse.useMonthApproverConfirm == true) {
							vm.monText = approvedDataExecution.monthDisplayAtr == true ? ko.observable(vm.$i18n('KTG001_5')) : ko.observable(vm.$i18n('KTG001_6'));
							vm.visible(approvedDataExecution.monthDisplayAtr);
						}

						if (i.item == ApprovedApplicationStatusItem.AGREEMENT_APPLICATION_DATA && i.displayType == NotUseAtr.USE) {
							vm.aggrText = approvedDataExecution.aggrDisplayAtr == true ? ko.observable(vm.$i18n('KTG001_5')) : ko.observable(vm.$i18n('KTG001_6'));
							vm.visible(approvedDataExecution.aggrDisplayAtr);
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



	}
}