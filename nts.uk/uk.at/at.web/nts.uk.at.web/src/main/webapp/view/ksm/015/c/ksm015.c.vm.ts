module nts.uk.at.view.ksm015.c.viewmodel {
	export class ScreenModel {

		multiSelectedWorkplaceId: KnockoutObservable<string>;
		baseDate: KnockoutObservable<Date>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		treeGrid: TreeComponentOption;
		shiftColumn: Array<any>;
		shiftItems: KnockoutObservableArray<ShiftMaster>;
		shiftGridColumns: KnockoutObservableArray<any>;
		constructor() {
			let self = this;
			self.baseDate = ko.observable(new Date());
			self.multiSelectedWorkplaceId = ko.observableArray([]);
			self.alreadySettingList = ko.observableArray([]);
			self.treeGrid = {
				isShowAlreadySet: true,
				isMultipleUse: true,
				isMultiSelect: false,
				treeType: 1,
				selectedWorkplaceId: self.multiSelectedWorkplaceId,
				baseDate: self.baseDate,
				selectType: 1,
				isShowSelectButton: true,
				isDialog: false,
				alreadySettingList: self.alreadySettingList,
				maxRows: 15,
				tabindex: 1,
				systemType: 2

			};

			let ksm015Data = new Ksm015Data();
			self.shiftItems = ko.observableArray(ksm015Data.mockShift);
			self.shiftGridColumns = ko.observableArray(ksm015Data.shiftGridColumns);
			$('#tree-grid').ntsTreeComponent(self.treeGrid);
		}

		startPage(): JQueryPromise<any> {
			let self = this;

			let dfd = $.Deferred();
			dfd.resolve(1);
			return dfd.promise();
		}
	}
}