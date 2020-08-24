@bean()
class Test extends ko.ViewModel {

	// filterList: KnockoutObservableArray<any>;
	// selectedWorkplaceId: KnockoutObservable<string>;
	// baseDate: KnockoutObservable<Date>;
	// treeGrid: TreeComponentOption;
	// date: KnockoutObservable<string>;
	// selectedFilter: KnockoutObservable<number>;
	// options: Option;
	// currentCodes: KnockoutObservable<any> = ko.observable([]);
	// currentNames: KnockoutObservable<any> = ko.observable([]);
	// alreadySettingList: KnockoutObservableArray<any> = ko.observableArray(['1']);
	// currentIds: KnockoutObservable<string> = ko.observable("");
	currentScreen: any = null;  
	constructor() {
		super();

		// let self = this;
		// self.filterList = ko.observableArray([
		// 	new BoxModel(0, '職場'),
		// 	new BoxModel(1, '職場グループ')
		// ]);

		// self.selectedWorkplaceId = ko.observable("");
		// self.date = ko.observable("");
		// self.baseDate = ko.observable(new Date());
		// self.treeGrid = {
		// 	isMultipleUse: true,
		// 	isMultiSelect: false,
		// 	treeType: 1,
		// 	selectedId: self.selectedWorkplaceId,
		// 	baseDate: self.baseDate,
		// 	selectType: 4,
		// 	isShowSelectButton: false,
		// 	isDialog: false,
		// 	maxRows: 15,
		// 	tabindex: 1,
		// 	systemType: 2
		// };


		// $('#tree-grid').ntsTreeComponent(self.treeGrid);
		// self.selectedFilter.subscribe((val) => {
		// 	let self = this;
		// 	if (val == 0) {
		// 		$("#tree-grid-2").hide();
		// 		$("#tree-grid").show();
		// 	} else {
		// 		$("#tree-grid-2").show();
		// 		$("#tree-grid").hide();
		// 	}
		// });
		// self.selectedFilter = ko.observable(0);

		// self.options = {
		// 	// neu muon lay code ra tu trong list thi bind gia tri nay vao
		// 	currentCodes: self.currentCodes,
		// 	currentNames: self.currentNames,
		// 	// tuong tu voi id
		// 	currentIds: self.currentIds,
		// 	//
		// 	multiple: false,
		// 	tabindex: 2,
		// 	isAlreadySetting: true,
		// 	alreadySettingList: self.alreadySettingList,
		// 	// show o tim kiem
		// 	showPanel: true,
		// 	// show empty item
		// 	showEmptyItem: false,
		// 	// trigger reload lai data cua component
		// 	reloadData: ko.observable(''),
		// 	height: 400,
		// 	// NONE = 0, FIRST = 1, ALL = 2
		// 	selectedMode: 1
		// };

	}

	public openDialogLA(): void {
                let self = this;
                nts.uk.ui.windows.setShared('dataShareDialogU', {
						baseDate: "2020/08/12",
						unit: 1,
						workplaceGroupId: "4dbab3f3-a8af-475f-a2c3-0a4101378284"
            	});
                self.currentScreen = nts.uk.ui.windows.sub.modal("/view/ksu/001/u/index.xhtml");
    }   
	public openModal(): void {
		let self = this;
		// let request = {
		// 	baseDate: self.date(),
		// 	unit: self.selectedFilter(),
        //     workplaceId: self.selectedFilter() == 0 ? self.selectedWorkplaceId() : null,
        //     workplaceGroupId: self.selectedFilter() == 1 ? self.currentIds(): null            
		// };
		let request = { baseDate: "2020/08/12",
						unit: 1,
						workplaceGroupId: "4dbab3f3-a8af-475f-a2c3-0a4101378284"
		                // workplaceGroupId: "57a38034-3840-4443-93b2-cc323a4feff6",
						// workplaceId: "2a08b71c-9e16-4e65-8e34-a78df6d67a01"
		         };  
		self.$window.modal("/view/ksu/001/u/index.xhtml", request);
	}
}

class BoxModel {
	id: number;
	name: string;
	constructor(id, name) {
		let self = this;
		self.id = id;
		self.name = name;
	}
}

