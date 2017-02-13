// TreeGrid Node
module qpp011.f {

    export class ScreenModel {
        //gridlist
        gridListItems_F_LST_001: KnockoutObservableArray<GridItemModel_F_LST_001>;
        columns_F_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        gridListCurrentCode_F_LST_001: KnockoutObservable<any>;
        currentCodeList_F_LST_001: KnockoutObservableArray<any>;
        //currencyeditor
        currencyeditor: any;
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            //gridlist data
            self.gridListItems_F_LST_001 = ko.observableArray([
                new GridItemModel_F_LST_001('001', '髯憺屮�ｽｽ�ｽｺ髫ｴ蟷｢�ｽｽ�ｽｬ鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('150', '髯溷私�ｽｽ�ｽｹ鬮｢�ｽｨ�ｿｽ�ｽｽ�ｽｷ髫ｰ�ｿｽ陷ｿ�ｽ･�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｿ�ｽｽ'),
                new GridItemModel_F_LST_001('ABC', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC1', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC2', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC3', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC4', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC5', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC6', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC7', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC8', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC9', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC10', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC11', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ'),
                new GridItemModel_F_LST_001('ABC12', '髯憺屮�ｽｽ�ｽｺ12髫ｴ蟷｢�ｽｽ�ｽｬghj鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ')
            ]);
            self.columns_F_LST_001 = ko.observableArray([
                { headerText: '驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｳ驛｢譎｢�ｽｽ�ｽｼ驛｢譎｢�ｽｿ�ｽｽ', prop: 'code', width: 100 },
                { headerText: '髯ｷ�ｽｷ陷･�ｽｲ�ｿｽ�ｽｽ�ｽｧ�ｿｽ�ｽｽ�ｽｰ', prop: 'name', width: 150 }
            ]);
            self.gridListCurrentCode_F_LST_001 = ko.observable();
            self.currentCodeList_F_LST_001 = ko.observableArray([]);
            //currencyeditor
            self.currencyeditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "USD",
                    currencyposition: 'right'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //Switch
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '邏堺ｻ伜�亥挨' },
                { code: '2', name: '蛟倶ｺｺ譏守ｴｰ蛻･' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
        submitDialog() {
            nts.uk.ui.windows.close();
        }
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
    export class GridItemModel_F_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
};
