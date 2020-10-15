module test.viewmodel {
	import setShare = nts.uk.ui.windows.setShared;
	import getShare = nts.uk.ui.windows.getShared;

	export class ScreenModel {
		input: any;
		fillter: KnockoutObservable<boolean>;
		treeGrid: TreeComponentOption;
		selectedWorkplaceId: KnockoutObservable<string>;
		baseDate: KnockoutObservable<Date>;
		currentScreen: any = null;
		showNone: KnockoutObservable<boolean> = ko.observable(false);
		showDeferred: KnockoutObservable<boolean> = ko.observable(false);
		initiallySelected: KnockoutObservable<string> = ko.observable('');
		constructor() {
			let self = this;
			self.fillter = ko.observable(false);
			self.input = [];
			self.baseDate = ko.observable(new Date());
			self.selectedWorkplaceId = ko.observable('');

			self.treeGrid = {
				isMultipleUse: true,
				isMultiSelect: false,
				treeType: 1,
				selectedId: self.selectedWorkplaceId,
				baseDate: self.baseDate,
				selectType: 4,
				isShowSelectButton: false,
				isDialog: false,
				maxRows: 15,
				tabindex: 1,
				systemType: 2

			};
			$('#tree-grid').ntsTreeComponent(self.treeGrid);
		}
		startPage(): JQueryPromise<any> {
			var self = this;
			var dfd = $.Deferred();
			dfd.resolve();
			return dfd.promise();
		}
		openDialog() {
			let self = this;
			let data: any = {};
			data.fillter = self.fillter();
			data.workPlaceId = self.selectedWorkplaceId();
			data.showNone = self.showNone();
			data.showDeferred = self.showDeferred();
			data.initiallySelected = self.initiallySelected();
			setShare('data', data);


			self.currentScreen = nts.uk.ui.windows.sub.modal("/view/kcp/013/component/index.xhtml");
		}
	}
}