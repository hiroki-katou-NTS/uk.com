module nts.uk.at.view.ktg001.b {
	import windows = nts.uk.ui.windows;
	import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
	
	import IResponse = nts.uk.at.view.ktg001.a.IResponse;
	import KTG001_API = nts.uk.at.view.ktg001.a.KTG001_API;
    import Item = nts.uk.at.view.ktg001.a.IApprovedAppStatusDetailedSetting;

	@bean()
	class ViewModel extends ko.ViewModel {

		columns: KnockoutObservableArray<NtsGridListColumn>;
		items: KnockoutObservableArray<Item>;
		title: KnockoutObservable<string> = ko.observable('');
		selectedSwitch: KnockoutObservable<number> = ko.observable(1);


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

			vm.$ajax(KTG001_API.GET_APPROVED_DATA_EXCECUTION, param).done((data: IResponse) => {
				if (data) {
					let approvedDataExecution = data.approvedDataExecutionResultDto;
					let approvalProcessingUse = data.approvalProcessingUseSetting;
					
					vm.title(approvedDataExecution.topPagePartName);
					vm.items(approvedDataExecution.approvedAppStatusDetailedSettings)
					
					
					
					

				}
			}).always(() => vm.$blockui("clear"));
		}
		
		
		submitAndCloseDialog() {

		}

		closeDialog() {
			const vm = this;
			vm.$window.close();
		}



}
	}