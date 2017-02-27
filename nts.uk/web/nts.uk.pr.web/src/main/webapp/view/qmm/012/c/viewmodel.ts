module qmm012.c.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        //combobox
        //001
        ComboBoxItemList_C_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_C_001: KnockoutObservable<string>;
        //012
        checked_C_012: KnockoutObservable<boolean>;
        //013
        checked_C_013: KnockoutObservable<boolean>;
        //014
        checked_C_014: KnockoutObservable<boolean>;
        //015
        checked_C_015: KnockoutObservable<boolean>;
        //016
        checked_C_016: KnockoutObservable<boolean>;
        //Switch
        //002 003 005 006 007 008 009 010
        roundingRules_C_002_003_005To010: KnockoutObservableArray<any>;
        selectedRuleCode_C_002: any;
        selectedRuleCode_C_003: any;
        selectedRuleCode_C_005: any;
        selectedRuleCode_C_006: any;
        selectedRuleCode_C_007: any;
        selectedRuleCode_C_008: any;
        selectedRuleCode_C_009: any;
        selectedRuleCode_C_010: any;
        //011
        roundingRules_C_011: KnockoutObservableArray<any>;
        selectedRuleCode_C_011: any;
        //017
        roundingRules_C_017: KnockoutObservableArray<any>;
        selectedRuleCode_C_017: any;
        //end switch
        //radiogroup
        //004
        RadioItemList_C_004: KnockoutObservableArray<any>;
        selectedId_C_004: KnockoutObservable<number>;
        //currencyeditor_C_001
        currencyeditor_C_001: any;
        //textarea
        textArea: KnockoutObservable<any>;
        //shared
        groupName: KnockoutObservable<any>;
        constructor() {
            var self = this;
            //start combobox data
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            //combobox data
            self.ComboBoxItemList_C_001 = ko.observableArray([
                new ComboboxItemModel('1', '課税'),
                new ComboboxItemModel('2', '非課税(限度あり）'),
                new ComboboxItemModel('3', '非課税(限度なし）'),
                new ComboboxItemModel('4', '通勤費(手入力)'),
                new ComboboxItemModel('5', '通勤費(定期券利用)')
            ]);
            self.selectedCode_C_001 = ko.observable('1');
            //end combobox data
            //start checkbox Data
            self.checked_C_012 = ko.observable(true);
            self.checked_C_013 = ko.observable(true);
            self.checked_C_014 = ko.observable(true);
            self.checked_C_015 = ko.observable(true);
            self.checked_C_016 = ko.observable(true);
            //end checkbox data
            //start Switch Data
            self.enable = ko.observable(true);
            //005 006 007 008 009 010
            self.roundingRules_C_002_003_005To010 = ko.observableArray([
                { code: '1', name: '対象' },
                { code: '2', name: '対象外' }
            ]);
            self.selectedRuleCode_C_002 = ko.observable(1);
            self.selectedRuleCode_C_003 = ko.observable(1);
            self.selectedRuleCode_C_005 = ko.observable(1);
            self.selectedRuleCode_C_006 = ko.observable(1);
            self.selectedRuleCode_C_007 = ko.observable(1);
            self.selectedRuleCode_C_008 = ko.observable(1);
            self.selectedRuleCode_C_009 = ko.observable(1);
            self.selectedRuleCode_C_010 = ko.observable(1);
            //011
            self.roundingRules_C_011 = ko.observableArray([
                { code: '1', name: 'ゼロを表示する' },
                { code: '2', name: 'ゼロを表示しない' }
            ]);
            self.selectedRuleCode_C_011 = ko.observable(1);
            //017
            self.roundingRules_C_017 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ' },
                { code: '3', name: '繧ｼ繝ｭ繧定｡ｨ' },
                { code: '4', name: '繧ｼ繝ｭ繧定｡ｨ' },
            ]);
            self.selectedRuleCode_C_017 = ko.observable(1);
            //endSwitch Data

            //start radiogroup data
            //004
            self.RadioItemList_C_004 = ko.observableArray([
                new BoxModel(1, '全員一律で指定する'),
                new BoxModel(2, '給与契約形態ごとに指定する')
            ]);
            self.selectedId_C_004 = ko.observable(1);
            //end radiogroup data
            //currencyeditor_C_001
            self.currencyeditor_C_001 = {
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
            self.textArea = ko.observable("");
            //end textarea
            self.selectedCode_C_001.subscribe(function(newValue) {
                $('#C_LBL_002').show();
                $('#C_Div_002').show();
                $('#C_BTN_003').show();
                $('#C_Div_004').show();
                switch (newValue) {
                    case '1':
                        $('#C_Div_001').hide();
                        break;
                    case '2':
                    case '3':
                    case '4':
                        $('#C_Div_001').show();
                        self.selectedRuleCode_C_017('1');
                        break;
                    case '5':
                        $('#C_Div_001').show();
                        $('#C_LBL_002').hide();
                        $('#C_Div_002').hide();
                        $('#C_Div_003').show();
                        $('#C_BTN_003').hide();
                        $('#C_Div_004').hide();
                        break;
                }
            })
            self.selectedRuleCode_C_017.subscribe(function(newValue) {
                $('#C_Div_002').hide();
                $('#C_Div_003').hide();
                switch (newValue) {
                    case '1':
                    case '3':
                        $('#C_Div_002').show();
                        break;
                    case '2':
                    case '4':
                        $('#C_Div_003').show();
                        break;
                }

            })
        }

        start(): JQueryPromise<any> {
            var self = this;
            // Page load dfd.
            var dfd = $.Deferred();
            //dropdownlist event

            //end switch event
        }
        openIDialog() {
            nts.uk.ui.windows.sub.modal('../i/index.xhtml', { height: 600, width: 1015, dialogClass: "no-close" }).onClosed(function(): any {

            });
        }
    }



    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            this.id = id;
            this.name = name;
        }
    }

    class GridItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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
}