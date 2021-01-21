module nts.uk.at.view.kcp013.a.viewmodel {
	import getShared = nts.uk.ui.windows.getShared;
	export class ScreenModel {
		input: any;
		fillter: boolean;
		workPlaceId: string; 
		constructor() {
			var self = this;
			self.fillter = true;
			self.workPlaceId = '';
			let data = getShared("data");
			self.input = {
				fillter: data.fillter,
				workPlaceId: ko.observable(data.workPlaceId),
				initiallySelected: [data.initiallySelected],
				displayFormat: '',
				showNone: data.showNone,
				showDeferred: data.showDeferred,
				selectMultiple: true,
				disable: true
			}
		}
		startPage(): JQueryPromise<any> {
			var self = this;
			var dfd = $.Deferred();
			dfd.resolve();
			return dfd.promise();
		}
		selectedWorkingHours(data, data2): void {

		}
		public closeDialog(): void {
			let self = this;
			nts.uk.ui.windows.close();
		}
	}
}