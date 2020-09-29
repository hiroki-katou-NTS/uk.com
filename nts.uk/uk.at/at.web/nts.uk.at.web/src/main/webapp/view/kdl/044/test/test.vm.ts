module test.viewmodel {

	import setShared = nts.uk.ui.windows.setShared;
	import IDataTransfer = nts.uk.at.view.kdl044.a.viewmodel.IDataTransfer;
	import getText = nts.uk.resource.getText;
	import getShared = nts.uk.ui.windows.getShared;

	export class ScreenModel {
		baseDate: KnockoutObservable<Date>;
		isMultiSelect: KnockoutObservable<boolean> = ko.observable(true);
		listShifuto: KnockoutObservableArray<Shifuto> = ko.observableArray([]);
		columns: KnockoutObservableArray<any>;
		selectedCodes: KnockoutObservableArray<String> = ko.observableArray([]);
		modeList: KnockoutObservableArray<any>;
		permissionList: KnockoutObservableArray<any>;
		selectedMode: KnockoutObservable<number>;
		selectedPer: KnockoutObservable<number>;
		enable: KnockoutObservable<boolean>;
		filterList: KnockoutObservableArray<any>;
		selectedFilter: KnockoutObservable<number>;
		listFilter: KnockoutObservableArray<Shifuto> = ko.observableArray([]);
		selectedFilterCodes: KnockoutObservableArray<String> = ko.observableArray([]);
		filterColumns: KnockoutObservableArray<any>;
		selectedCode: KnockoutObservable<string> = ko.observable("");
		treeGrid: TreeComponentOption;
		selectedWorkplaceId: KnockoutObservable<string>;


		shiftItems: KnockoutObservableArray<ShiftMaster>;
		selectedShiftMaster: KnockoutObservableArray<any>;
		shiftColumns: Array<any>;
		currentIds: KnockoutObservable<any> = ko.observable([]);
		currentCodes: KnockoutObservable<any> = ko.observable([]);
		currentNames: KnockoutObservable<any> = ko.observable([]);
		alreadySettingList: KnockoutObservableArray<any> = ko.observable(['1']);
		isWorkPlGrp: KnockoutObservable<boolean> = ko.observable(false);
		options: Option;
		constructor() {
			let self = this;

			self.shiftColumns = ko.observableArray([
				// ver 17
				{ headerText: nts.uk.resource.getText('KSM015_13'), key: 'shiftMasterCode', width: 50, formatter: _.escape},
				{ headerText: nts.uk.resource.getText('KSM015_14'), key: 'shiftMasterName', width: 50, formatter: _.escape},
				{ headerText: nts.uk.resource.getText('KSM015_15'), key: 'workTypeName', width: 100, hidden: true , formatter: _.escape},
				{ headerText: nts.uk.resource.getText('KSM015_16'), key: 'workTimeName', width: 100, hidden: true , formatter: _.escape},
				{ headerText: nts.uk.resource.getText('KSM015_32'), key: 'workTime1', width: 200 , formatter: _.escape},
				{ headerText: nts.uk.resource.getText('KSM015_33'), key: 'workTime2', width: 200 , formatter: _.escape},
				{ headerText: nts.uk.resource.getText('KSM015_20'), key: 'remark', width: 200 , formatter: _.escape}
			]);
			self.shiftItems = ko.observableArray([]);
			self.selectedShiftMaster = ko.observableArray([]);
			self.options = {
				// neu muon lay code ra tu trong list thi bind gia tri nay vao
				currentCodes: self.currentCodes,
				currentNames: self.currentNames,
				// tuong tu voi id
				currentIds: self.currentIds,
				//
				multiple: false,
				tabindex: 2,
				isAlreadySetting: true,
				alreadySettingList: self.alreadySettingList,
				// show o tim kiem
				showPanel: true,
				// show empty item
				showEmptyItem: false,
				// trigger reload lai data cua component
				reloadData: ko.observable(''),
				height: 400,
				// NONE = 0, FIRST = 1, ALL = 2
				selectedMode: 1
			};

			self.baseDate = ko.observable(new Date());
			self.selectedWorkplaceId = ko.observableArray("");
			self.selectedWorkplaceId.subscribe((val) => {
				if (val && self.selectedFilter() == 1) {
					let param = {
						workplaceId: val,
						targetUnit: 0
					}
					service.getShiftMaster(param)
						.done((data) => {
							self.listShifuto(_.sortBy(data, 'shiftMastercode'));
							self.selectedCodes([]);
						});
				}
			});
			self.columns = ko.observableArray([
				{ headerText: getText('KDL044_2'), key: "shiftMasterCode", dataType: "string", width: 50, formatter: _.escape },
				{ headerText: getText('KDL044_3'), key: "shiftMasterName", dataType: "string", width: 100, formatter: _.escape },
				{ headerText: getText('KDL044_6'), key: "workTime1", dataType: "string", width: 200, formatter: _.escape },
				{ headerText: getText('KDL044_7'), key: "workTime2", dataType: "string", width: 200 , formatter: _.escape},
				{ headerText: getText('KDL044_8'), key: "remark", dataType: "string", width: 200, formatter: _.escape }
			]);

			self.filterColumns = ko.observableArray([
				{ headerText: getText('KDL044_2'), key: "code", dataType: "string", width: 50, formatter: _.escape },
				{ headerText: getText('KDL044_3'), key: "name", dataType: "string", width: 100, formatter: _.escape }
			]);
			self.modeList = ko.observableArray([
				new BoxModel(1, '複数選択'),
				new BoxModel(2, '単一選択')
			]);
			self.permissionList = ko.observableArray([
				new BoxModel(1, '選択肢[なし]を表示する'),
				new BoxModel(0, '選択肢[なし]を表示しない')
			]);
			self.filterList = ko.observableArray([
				new BoxModel(0, '絞り込みしない'),
				new BoxModel(1, '職場'),
				new BoxModel(2, '職場グループ')
			]);
			self.selectedMode = ko.observable(1);
			self.selectedMode.subscribe((value) => {
				if (value == 1) {
					self.isMultiSelect(true);
				}
				else {
					self.isMultiSelect(false);
				}});
			self.selectedPer = ko.observable(1);
			self.selectedFilter = ko.observable(0);
			self.enable = ko.observable(true);

			self.selectedCodes.subscribe((val) => {
				const a = val;
				console.log("aaa");
			})

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

			self.currentIds.subscribe((val) => {
				let self = this;
				if (val) {
					let param = {
						workplaceGroupId: self.currentIds().length == 0 ? "" : self.currentIds(),
						targetUnit: 1
					}
					if (param.workplaceGroupId != "") {
						service.getShiftMasterByWplGroup(param)
							.done((data) => {
								data = _.sortBy(data, 'shiftMasterCode');
								self.shiftItems(data);
								self.selectedShiftMaster([]);
							});
					}
				}
			});

			self.selectedFilter.subscribe((val) => {
				let self = this;
				if (val == 0 || val == 1) {
					self.isWorkPlGrp(false);
					$("#workplace-group").hide();
					$("#multi-list-table").show();
					if (val == 0) {
						let param = { targetUnit: null, workplaceIds: null, workplaceGroupID: null };
						service.getShiftMaster(param)
							.done((data) => {
								self.listShifuto(_.sortBy(data, 'shiftMastercode'));
							});
							self.selectedWorkplaceId("");
					} else {
						let lwps = $('#tree-grid').getDataList();
						self.selectedWorkplaceId(lwps[0].id);
						let param = {
						workplaceId: lwps[0].id,
						targetUnit: 0
					}
					service.getShiftMaster(param)
						.done((data) => {
							self.listShifuto(_.sortBy(data, 'shiftMastercode'));
							self.selectedCodes([]);
						});
					}
				} else {
					self.isWorkPlGrp(true);
					$("#workplace-group").show();
					$("#multi-list-table").hide();
				}
			})

			$('#tree-grid').ntsTreeComponent(self.treeGrid);
		}

		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			let param = { targetUnit: null, workplaceIds: null, workplaceGroupID: null };

			$("#multi-list-table").show();
			$("#workplace-group").hide();
			$("#signle-list-table").hide();

			service.getShiftMaster(param)
				.done((data) => {
					self.listShifuto(_.sortBy(data, 'shiftMastercode'));
				});

			dfd.resolve();

			return dfd.promise();
		}
		openDialog() {
			let self = this;
			nts.uk.ui.block.invisible();
			let shifutoCodes = null;
			if (self.selectedFilter() == 1 || self.selectedFilter() == 0) {
				if (self.isMultiSelect() == true) {
					shifutoCodes = self.selectedCodes();
				} else {
					shifutoCodes = self.selectedCode()
				}
			} else {
				shifutoCodes = self.selectedShiftMaster();
			}
			
			let permissions = true;
			if(self.isMultiSelect() == true){
				permissions = false;
			}
			if(self.selectedPer() == 0 && self.isMultiSelect() == false){
				permissions = false;
			}
			let dataSetShare: IDataTransfer = {
				isMultiSelect: self.selectedMode() == 1 ? true : false,
				permission: permissions,
				filter: self.selectedFilter(),
				filterIDs: self.selectedFilter() == 2 ? [self.currentIds()] : [self.selectedWorkplaceId()],
				shifutoCodes: [],
				workPlaceType: self.selectedFilter() ,
				shiftCodeExpel: shifutoCodes
			}
			setShared('kdl044Data', dataSetShare);
			nts.uk.ui.windows.sub.modal("/view/kdl/044/a/index.xhtml", { dialogClass: "no-close" })
				.onClosed(() => {
					let isCancel = getShared('kdl044_IsCancel') != null ? getShared('kdl044_IsCancel') : true;
					if (!isCancel) {
						let returnedData = getShared('kdl044ShifutoCodes');
						if (self.isMultiSelect()) {
							self.selectedCodes([]);
							self.selectedCodes(returnedData);
						} else {
							self.selectedCode(returnedData);
						}
					}
					nts.uk.ui.block.clear();
				});
		}
	}

	class Shifuto {
		shiftMasterCode: string;
		shiftMasterName: string;
		workTypeName: string;
		workTimeName: string;
		workTime1: string;
		workTime2: string;
		remark: string;
		constructor(code: string,
			name: string,
			workType: string,
			workTime: string,
			time1: string,
			time2: string,
			remark: string) {
			let self = this;
			self.shiftMasterCode = code;
			self.shiftMasterName = name;
			self.workTypeName = workType;
			self.workTimeName = workTime;
			self.workTime1 = time1;
			self.workTime2 = time2;
			self.remark = remark;
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

	class Filter {
		code: string;
		name: string;
		constructor(code, name) {
			let self = this;
			self.code = code;
			self.name = name;
		}
	}

}