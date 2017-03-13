module qmm012.e.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        currencyeditor_E_001: any;
        //E_004
        checked_E_004: KnockoutObservable<boolean>;
        //E_005
        checked_E_005: KnockoutObservable<boolean>;
        //E_006
        checked_E_006: KnockoutObservable<boolean>;
        //E_007
        checked_E_007: KnockoutObservable<boolean>;
        //E_008
        checked_E_008: KnockoutObservable<boolean>;
        //E_001To003
        roundingRules_E_001: KnockoutObservableArray<any>;
        roundingRules_E_002: KnockoutObservableArray<any>;
        roundingRules_E_003: KnockoutObservableArray<any>;
        selectedRuleCode_E_001: any;
        selectedRuleCode_E_002: any;
        selectedRuleCode_E_003: any;
        textArea_E_005: any;
        constructor() {
            let self = this;
            //E_004
            self.checked_E_004 = ko.observable(true);
            //E_005
            self.checked_E_005 = ko.observable(true);
            //E_006
            self.checked_E_006 = ko.observable(true);
            //E_007
            self.checked_E_007 = ko.observable(true);
            //E_008
            self.checked_E_008 = ko.observable(true);
            //E_001 To 003
            //E_001To003
            self.roundingRules_E_001 = ko.observableArray([
                { code: '1', name: '譎る俣' },
                { code: '2', name: '蝗樊焚' }
            ]);
            self.roundingRules_E_002 = ko.observableArray([
                { code: '1', name: '蟇ｾ雎｡' },
                { code: '2', name: '髱槫ｯｾ遘ｰ' }
            ]);
            self.roundingRules_E_003 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
            ]);
            self.selectedRuleCode_E_001 = ko.observable(1);
            self.selectedRuleCode_E_002 = ko.observable(1);
            self.selectedRuleCode_E_003 = ko.observable(1);
            self.enable = ko.observable(true);
            self.currencyeditor_E_001 = {
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
            self.textArea_E_005 = ko.observable("");
        }
        
    }


}