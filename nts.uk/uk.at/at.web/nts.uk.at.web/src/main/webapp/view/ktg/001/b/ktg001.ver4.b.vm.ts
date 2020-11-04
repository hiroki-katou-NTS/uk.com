module nts.uk.at.view.ktg001.b {
	import windows = nts.uk.ui.windows;
	import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;

	import IResponse = nts.uk.at.view.ktg001.a.IResponse;
	import API = nts.uk.at.view.ktg001.a.KTG001_API;
	import Item = nts.uk.at.view.ktg001.a.IApprovedAppStatusDetailedSetting;

	interface UpdateParam {
		topPagePartName: string; //名称
		items: Array<Item>; //承認すべき申請状況の詳細設定
	}
	const USE = __viewContext.enums.NotUseAtr[1].value;
	const APP = __viewContext.enums.ApprovedApplicationStatusItem[0].value;
	const DAY = __viewContext.enums.ApprovedApplicationStatusItem[1].value;
	const MON = __viewContext.enums.ApprovedApplicationStatusItem[2].value;
	const AGG = __viewContext.enums.ApprovedApplicationStatusItem[3].value;

	@bean()
	class ViewModel extends ko.ViewModel {

		columns: KnockoutObservableArray<NtsGridListColumn>;
		updateParam: KnockoutObservable<UpdateParam>;
		title: KnockoutObservable<string> = ko.observable('');
		selectedSwitch: KnockoutObservable<number> = ko.observable(1);

		appChecked: KnockoutObservable<Boolean> = ko.observable(false);
		dayChecked: KnockoutObservable<Boolean> = ko.observable(false);
		monChecked: KnockoutObservable<Boolean> = ko.observable(false);
		aggrChecked: KnockoutObservable<Boolean> = ko.observable(false);

		dayRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
		monRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
		aggrRowVisible: KnockoutObservable<Boolean> = ko.observable(false);


		created() {
			const vm = this;
			vm.columns = ko.observableArray([
				{ headerText: vm.$i18n('KTG001_8'), key: 'item', width: 150 },
				{ headerText: vm.$i18n('KTG001_9'), key: 'display', width: 50 }
			]);
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
			vm.$blockui("grayout");
			vm.$ajax(API.GET_APPROVED_DATA_EXCECUTION, param).done((data: IResponse) => {
				let approvalProcessingUse = data.approvalProcessingUseSetting;
				let agreementOperationSetting = data.agreementOperationSetting;
				
				vm.dayRowVisible(approvalProcessingUse.useDayApproverConfirm);
				vm.monRowVisible(approvalProcessingUse.useMonthApproverConfirm);
				vm.aggrRowVisible(agreementOperationSetting.specicalConditionApplicationUse);
				
				if (data.approvedDataExecutionResultDto) {
					let approvedDataExecution = data.approvedDataExecutionResultDto;

					vm.title(approvedDataExecution.topPagePartName);

					if(approvedDataExecution.approvedAppStatusDetailedSettings) {
							approvedDataExecution.approvedAppStatusDetailedSettings.forEach(s => {
						if (s.item == APP) {
							vm.appChecked(s.displayType == USE ? true : false);
						}

						if (s.item == DAY) {
							vm.dayChecked(s.displayType == USE ? true : false);
						}

						if (s.item == MON) {
							vm.monChecked(s.displayType == USE ? true : false);
						}

						if (s.item == AGG) {
							vm.aggrChecked(s.displayType == USE ? true : false);
						}

					})
					}
				}
			}).always(() => vm.$blockui("clear"));


		}

		submitDialog() {
			let vm = this, updateParam = {
				topPagePartName: vm.title(),
				approvedAppStatusDetailedSettings: [{ displayType: vm.appChecked() == true ? 1 : 0, item: APP.value },
				{ displayType: vm.dayChecked() == true ? 1 : 0, item: DAY },
				{ displayType: vm.monChecked() == true ? 1 : 0, item: MON },
				{ displayType: vm.aggrChecked() == true ? 1 : 0, item: AGG },
				]
			};

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}
				vm.$ajax(API.UPDATE_APPROVED_DATA_EXCECUTION, updateParam).done(() => {
				}).always(() => {
					vm.$blockui("clear");
				});

			});
		}

		closeDialog() {
			const vm = this;
			vm.$window.close();
		}



	}
}