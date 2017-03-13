module qmm012.d.viewmodel {
    export class ScreenModel {
        isEditable: KnockoutObservable<boolean>;
        isEnable: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        //001
        ComboBoxItemList_D_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_D_001: KnockoutObservable<string>;
        //Checkbox
        //D_003
        checked_D_003: KnockoutObservable<boolean>;
        //D_003
        checked_D_004: KnockoutObservable<boolean>;
        //D_003
        checked_D_005: KnockoutObservable<boolean>;
        //D_003
        checked_D_006: KnockoutObservable<boolean>;
        //D_003
        checked_D_007: KnockoutObservable<boolean>;
        //Switch
        currencyeditor_D_001: any;
        //D_002
        roundingRules_D_002: KnockoutObservableArray<any>;
        selectedRuleCode_D_002: any;
        //textarea
        textArea_D_005: KnockoutObservable<any>;
        //shared
        groupName: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.isEditable = ko.observable(true);
            self.isEnable = ko.observable(true);
            self.enable = ko.observable(true);
            self.selectedCode_D_001 = ko.observable('1');
            self.ComboBoxItemList_D_001 = ko.observableArray([
                new ComboboxItemModel('1', '莉ｻ諢乗而髯､鬆�逶ｮ'),
                new ComboboxItemModel('2', '遉ｾ莨壻ｿ晞匱鬆�逶ｮ'),
                new ComboboxItemModel('3', '謇�蠕礼ｨ朱��逶ｮ'),
                new ComboboxItemModel('4', '菴乗ｰ醍ｨ朱��逶ｮ')
            ]);
            self.selectedCode_D_001 = ko.observable('1');
            //end combobox data
            //D_003
            self.checked_D_003 = ko.observable(true);
            //D_004
            self.checked_D_004 = ko.observable(true);
            //D_005
            self.checked_D_005 = ko.observable(true);
            //D_006
            self.checked_D_006 = ko.observable(true);
            //D_006
            self.checked_D_007 = ko.observable(true);
            //D_002
            self.roundingRules_D_002 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
            ]);
            self.selectedRuleCode_D_002 = ko.observable(1);
            self.currencyeditor_D_001 = {
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
            //start textarea
            self.textArea_D_005 = ko.observable("");
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