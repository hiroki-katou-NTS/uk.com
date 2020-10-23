module nts.uk.at.view.ktg001.a {

	import windows = nts.uk.ui.windows;

	export const KTG001_API = {
		GET_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/display',
		UPDATE_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/setting',
	};

	//承認すべきデータの実行結果
	export interface IApprovedDataExecutionResult {
		haveParticipant: Boolean; //勤怠担当者である
		topPagePartName: string; //名称
		appDisplayAtr: Boolean; //承認すべき申請データ
		dayDisplayAtr: Boolean; //承認すべき日の実績が存在する
		monthDisplayAtr: Boolean; //承認すべき月の実績が存在する
		agrDisplayAtr: Boolean; //承認すべき36協定が存在する
		approvedAppStatusDetailedSettings: Array<IApprovedAppStatusDetailedSetting>; //承認すべき申請状況の詳細設定
		closingPeriods: Array<IClosureIdPresentClosingPeriod>; //締めID, 現在の締め期間
	}

	//承認すべき申請状況の詳細設定
	export interface IApprovedAppStatusDetailedSetting {
		displayType: number; //表示区分
		item: number; //項目
	}
	
	//List＜締めID, 現在の締め期間＞
	export interface IClosureIdPresentClosingPeriod {
		closureId: number; //締めID
		currentClosingPeriod: IPresentClosingPeriodImport; //現在の締め期間
	}

	//現在の締め期間
	export interface IPresentClosingPeriodImport {
		processingYm: number; //処理年月
		closureStartDate: String; //締め開始日
		closureEndDate: String; //締め終了日
	}

	//承認すべきデータのウィジェットを起動する
	export interface IResponse {
		approvedDataExecutionResultDto: IApprovedDataExecutionResult; //承認すべきデータのウィジェットを起動する
		approvalProcessingUseSetting: IApprovalProcessingUseSetting; //承認処理の利用設定を取得する
		//ドメインモデル「３６協定運用設定」を取得する
		//Update later
	}

	//承認処理の利用設定を取得する
	export interface IApprovalProcessingUseSetting {
		useDayApproverConfirm: Boolean; //日の承認者確認を利用する
		useMonthApproverConfirm: Boolean; //月の承認者確認を利用する
	}

	interface IParam {
		ym: number, //表示期間
		closureId: number //締めID
	}
	
	const USE = __viewContext.enums.NotUseAtr[1].value;
	const APP = __viewContext.enums.ApprovedApplicationStatusItem[0].value;
	const DAY = __viewContext.enums.ApprovedApplicationStatusItem[1].value;
	const MON = __viewContext.enums.ApprovedApplicationStatusItem[2].value;
	const AGG = __viewContext.enums.ApprovedApplicationStatusItem[3].value;


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

		param: IParam;

		created() {
			let vm = this;
			let cacheCcg008 = windows.getShared("cache");
			let closureId = 1;

			vm.param = {
				ym: vm.selectedSwitch(),
				closureId: closureId

			};

			if (!cacheCcg008 || !cacheCcg008.currentOrNextMonth) {
				vm.selectedSwitch(1);
			} else {
				vm.selectedSwitch(cacheCcg008.currentOrNextMonth);
				closureId = cacheCcg008.closureId;
			}
		}

		mounted() {
			let vm = this;
			vm.loadData();
		}

		loadData(): void {
			let vm = this;
			vm.$blockui("grayout");
			vm.$ajax(KTG001_API.GET_APPROVED_DATA_EXCECUTION, vm.param).done((data: IResponse) => {
				if (data) {
					let approvedDataExecution = data.approvedDataExecutionResultDto;
					let approvalProcessingUse = data.approvalProcessingUseSetting;

					vm.title(approvedDataExecution.topPagePartName);
					vm.appText(approvedDataExecution.appDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
					vm.dayText(approvedDataExecution.dayDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
					vm.monText(approvedDataExecution.monthDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
					vm.aggrText(approvedDataExecution.agrDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));

					approvedDataExecution.approvedAppStatusDetailedSettings.forEach(i => {
						if (i.item == APP) {
							vm.appRowVisible(i.displayType == USE);
							vm.appIconVisible(i.displayType == USE && approvedDataExecution.appDisplayAtr == true ? true : false);
						}

						if (i.item == DAY) {
							vm.dayRowVisible(i.displayType == USE && approvalProcessingUse.useDayApproverConfirm == true);
							vm.dayIconVisible(i.displayType == USE && approvalProcessingUse.useDayApproverConfirm == true && approvedDataExecution.dayDisplayAtr == true ? true : false);
						}

						if (i.item == MON) {
							vm.monRowVisible(i.displayType == USE && approvalProcessingUse.useMonthApproverConfirm == true);
							vm.monIconVisible(i.displayType == USE && approvalProcessingUse.useMonthApproverConfirm == true && approvedDataExecution.monthDisplayAtr == true ? true : false);
						}

						if (i.item == AGG) {
							//update later
							//vm.aggrRowVisible(i.displayType == USE && ...);
							vm.aggrRowVisible(true);
							vm.aggrIconVisible(i.displayType == USE && approvedDataExecution.agrDisplayAtr == true ? true : false);
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
			//Update later
		}

		setting() {
			let vm = this;
			nts.uk.ui.windows.sub.modal('at', '/view/ktg/001/b/index_ver4.xhtml').onClosed(() => {
				vm.loadData();
			});
		}



	}
}