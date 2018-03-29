module nts.uk.at.view.kaf018.a.viewmodel {
    import text = nts.uk.resource.getText;
    var lstWkp = [];
    export class ScreenModel {
        targets: Array<model.ItemModel>;
        selectTarget: number;
        items: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        selectedValue1: KnockoutObservable<boolean>;
        selectedValue2: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        kcp004WorkplaceListOption: any;
        baseDate: KnockoutObservable<Date>;
        selectedWorkplaceId: KnockoutObservableArray<String>;
        alreadySettingList: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.targets = [
                new model.ItemModel(0, 'Anh'),
                new model.ItemModel(1, 'Duc'),
                new model.ItemModel(2, 'Tuan'),
                new model.ItemModel(3, 'Anh'),
                new model.ItemModel(4, 'Anh'),

            ];
            self.selectTarget = 1;
            self.items = ko.observableArray([
                new model.ItemModel(0, text('KAF018_12')),
                new model.ItemModel(1, text('KAF018_13'))
            ]);
            self.baseDate = ko.observable(new Date());
            self.enable = ko.observable(true);
            self.checked = ko.observable(true);
            self.selectedValue1 = ko.observable(true);
            self.selectedValue2 = ko.observable(false);
            self.selectedWorkplaceId = ko.observableArray([]);
            self.alreadySettingList = ko.observableArray([]);
            self.kcp004WorkplaceListOption = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: true,
                selectedWorkplaceId: self.selectedWorkplaceId,
                isShowSelectButton: true,
                baseDate: self.baseDate,
                selectType: 3,
                showIcon: true,
                isDialog: true,
                alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 1,
                systemType: 2,
                treeType: 1
            };
            $('#wkp-list').ntsTreeComponent(self.kcp004WorkplaceListOption).done(() => {
                self.reloadData();
                $('#wkp-list').focusTreeGridComponent();
            });


        }

        reloadData() {
            var self = this;
            lstWkp = self.flattenWkpTree(_.cloneDeep($('#wkp-list').getDataList()));
            nts.uk.ui.block.invisible();
            nts.uk.at.view.kaf018.a.service.getAll(lstWkp.map((wkp) => { return wkp.workplaceId; })).done((dataResults: Array<IApplicationApprovalSettingWkp>) => {
                self.alreadySettingList(dataResults.map((data) => { return { workplaceId: data.wkpId, isAlreadySetting: true }; }));
                self.selectedWorkplaceId.valueHasMutated();
                nts.uk.ui.block.clear();
            });
        }

        flattenWkpTree(wkpTree) {
            return wkpTree.reduce((wkp, x) => {
                wkp = wkp.concat(x);
                if (x.childs && x.childs.length > 0) {
                    wkp = wkp.concat(this.flattenWkpTree(x.childs));
                    x.childs = [];
                }
                return wkp;
            }, []);
        }

        gotoH() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kaf/018/h/index.xhtml");
        }

        gotoB() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kaf/018/b/index.xhtml");
        }
    }

    export interface IApplicationApprovalSettingWkp {
        // 会社ID
        companyId: string;
        // 職場ID
        wkpId: string;
        // 選択
        selectionFlg: number;
    }

    export module model {
        export class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }


}