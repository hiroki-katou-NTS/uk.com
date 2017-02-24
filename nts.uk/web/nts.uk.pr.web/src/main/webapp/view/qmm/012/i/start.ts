__viewContext.ready(function() {
    class ScreenModel {
        //textediter
        texteditor: any;
        //combobox
        ComboBoxItemList: KnockoutObservableArray<ComboboxItemModel>;
        itemName: KnockoutObservable<string>;
        ComboBoxCurrentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //001
        ComboBoxItemList_001: KnockoutObservableArray<ComboboxItemModel>;
        itemName_001: KnockoutObservable<string>;
        ComboBoxCurrentCode_001: KnockoutObservable<number>
        selectedCode_001: KnockoutObservable<string>;
        //Checkbox
        checked: KnockoutObservable<boolean>;
        //002
        checked_002: KnockoutObservable<boolean>;
        //003
        checked_003: KnockoutObservable<boolean>;
        //012
        checked_012: KnockoutObservable<boolean>;
        //013
        checked_013: KnockoutObservable<boolean>;
        //014
        checked_014: KnockoutObservable<boolean>;
        //015
        checked_015: KnockoutObservable<boolean>;
        //016
        checked_016: KnockoutObservable<boolean>;
        //gridlist
        gridListItems: KnockoutObservableArray<GridItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        gridListCurrentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        //002 003 005 006 007 008 009 010
        roundingRules_002_003_005To010: KnockoutObservableArray<any>;
        selectedRuleCode_002: any;
        selectedRuleCode_003: any;
        selectedRuleCode_005: any;
        selectedRuleCode_006: any;
        selectedRuleCode_007: any;
        selectedRuleCode_008: any;
        selectedRuleCode_009: any;
        selectedRuleCode_010: any;
        //011
        roundingRules_011: KnockoutObservableArray<any>;
        selectedRuleCode_011: any;
        //017
        roundingRules_017: KnockoutObservableArray<any>;
        selectedRuleCode_017: any;
        //end switch
        selectedRuleCode: any;
        //radiogroup
        RadioItemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        //004
        RadioItemList_004: KnockoutObservableArray<any>;
        selectedId_004: KnockoutObservable<number>;
        //currencyeditor
        currencyeditor: any;
        //textarea
        textArea: KnockoutObservable<any>;
        //search box 
        filteredData: any;
        constructor() {
            var self = this;
            //textediter
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "60px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //start combobox data
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //001
            self.ComboBoxItemList_001 = ko.observableArray([
                new ComboboxItemModel('1', '課税'),
                new ComboboxItemModel('2', '非課税(限度あり）'),
                new ComboboxItemModel('3', '非課税(限度なし）'),
                new ComboboxItemModel('4', '通勤費(手入力)'),
                new ComboboxItemModel('5', '通勤費(定期券利用)')
            ]);
            self.ComboBoxCurrentCode_001 = ko.observable(1);
            self.selectedCode_001 = ko.observable('1')
            //end combobox data
            //start checkbox Data
            self.checked_002 = ko.observable(true);
            self.checked_003 = ko.observable(true);
            self.checked_012 = ko.observable(true);
            self.checked_013 = ko.observable(true);
            self.checked_014 = ko.observable(true);
            self.checked_015 = ko.observable(true);
            self.checked_016 = ko.observable(true);

            //end checkbox data
            // start gridlist
            this.gridListItems = ko.observableArray([
                new GridItemModel('001', 'Item1'),
                new GridItemModel('002', 'Item2'),
                new GridItemModel('003', 'Item3'),
                new GridItemModel('004', 'Item4'),
                new GridItemModel('005', 'Item5'),
                new GridItemModel('006', 'Item6'),
                new GridItemModel('007', 'Item7'),
                new GridItemModel('008', 'Item8'),
                new GridItemModel('009', 'Item9'),
                new GridItemModel('010', 'Item10'),
                new GridItemModel('011', 'Item11'),
                new GridItemModel('012', 'Item12'),
                new GridItemModel('013', 'Item13')
            ]);
            this.columns = ko.observableArray([
                { headerText: '驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｳ驛｢譎｢�ｽｽ�ｽｼ驛｢譎｢�ｽｿ�ｽｽ', prop: 'code', width: 100 },
                { headerText: '髯ｷ�ｽｷ陷･�ｽｲ�ｿｽ�ｽｽ�ｽｧ�ｿｽ�ｽｽ�ｽｰ', prop: 'name', width: 150 }
            ]);
            this.gridListCurrentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            //end gridlist
            //start Switch Data
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '謾ｯ邨ｦ' },
                { code: '2', name: '謾ｯ邨ｦ' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //005 006 007 008 009 010
            roundingRules_002_003_005To010 = ko.observableArray([
                { code: '1', name: '対象' },
                { code: '2', name: '対象外' }
            ]);
            self.selectedRuleCode_002 = ko.observable(1);
            self.selectedRuleCode_003 = ko.observable(1);
            self.selectedRuleCode_005 = ko.observable(1);
            self.selectedRuleCode_006 = ko.observable(1);
            self.selectedRuleCode_007 = ko.observable(1);
            self.selectedRuleCode_008 = ko.observable(1);
            self.selectedRuleCode_009 = ko.observable(1);
            self.selectedRuleCode_010 = ko.observable(1);
            //011
            roundingRules_011 = ko.observableArray([
                { code: '1', name: 'ゼロを表示する' },
                { code: '2', name: 'ゼロを表示しない' }
            ]);
            self.selectedRuleCode_011 = ko.observable(1);
            //017
            self.roundingRules_017 = ko.observableArray([
                { code: '1', name: '項目区分' },
                { code: '2', name: '項目区分' },
                { code: '3', name: '項目区分' },
                { code: '4', name: '項目区分' },
            ]);
            self.selectedRuleCode_017 = ko.observable(1);
            //endSwitch Data
            //start radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '隴幢ｽｬ驕会ｽｾ'),
                new BoxModel(2, '雎募供�ｽｮ螟奇ｽｪ�ｽｿ隴厄ｽｸ陷�ｽｺ陷牙ｸｷ逡題耳螟ゑｽ､�ｽｾ')
            ]);
            self.selectedId = ko.observable(1);
            //004
            self.RadioItemList_004 = ko.observableArray([
                new BoxModel(1, '全員一律で指定する'),
                new BoxModel(2, '給与契約形態ごとに指定する')
            ]);
            self.selectedId_004 = ko.observable(1);
            //end radiogroup data
            //currencyeditor
            self.currencyeditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //end currencyeditor
            //start textarea
            this.textArea = ko.observable("");
            //end textarea
            // start search box 
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.gridListItems(), "childs"));
            // end search box 
        }
    }
    class GridItemModel {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }

    }
    class ComboboxItemModel {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    this.bind(new ScreenModel());
});
function OpenModeSubWindow(url: string, option?: any) {
    nts.uk.ui.windows.sub.modal(url, option);
}
function closeDialog(): any {
    nts.uk.ui.windows.close();
}