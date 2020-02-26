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
        constructor() {
            let self = this;
            self.baseDate = ko.observable(new Date());
            self.selectedWorkplaceId = ko.observableArray("");
            self.selectedWorkplaceId.subscribe((val) => {
				if (val) {
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
                { headerText: getText('KDL044_2'), key: "shiftMasterCode", dataType: "string", width: 50 },
                { headerText: getText('KDL044_3'), key: "shiftMasterName", dataType: "string", width: 100 },
                { headerText: getText('KDL044_4'), key: "workTypeName", dataType: "string", width: 100 },
                { headerText: getText('KDL044_5'), key: "workTimeName", dataType: "string", width: 100 },
                { headerText: getText('KDL044_6'), key: "workTime1", dataType: "string", width: 150 },
                { headerText: getText('KDL044_7'), key: "workTime2", dataType: "string", width: 150 },
                { headerText: getText('KDL044_8'), key: "remark", dataType: "string", width: 200 }
            ]);

            self.filterColumns = ko.observableArray([
                { headerText: getText('KDL044_2'), key: "code", dataType: "string", width: 50 },
                { headerText: getText('KDL044_3'), key: "name", dataType: "string", width: 100 }
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
                if (value == 1)
                    self.isMultiSelect(true);
                else
                    self.isMultiSelect(false);
            });
            self.selectedPer = ko.observable(1);
            self.selectedFilter = ko.observable(0);
            self.enable = ko.observable(true);

            self.treeGrid = {
				isMultipleUse: true,
				isMultiSelect: false,
				treeType: 1,
				selectedWorkplaceId: self.selectedWorkplaceId,
				baseDate: self.baseDate,
				selectType: 4,
				isShowSelectButton: true,
				isDialog: false,
				maxRows: 15,
				tabindex: 1,
				systemType: 2

			};

            $('#tree-grid').ntsTreeComponent(self.treeGrid);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let param = {targetUnit: null, workplaceIds: null, workplaceGroupID: null};
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
            let dataSetShare: IDataTransfer = {
                isMultiSelect: self.selectedMode() == 1 ? true : false,
                permission: self.selectedPer() == 1 ? true : false,
                filter: self.selectedFilter(),
                filterIDs: [self.selectedWorkplaceId()],
                shifutoCodes: self.isMultiSelect() ? self.selectedCodes() : self.selectedCode()
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