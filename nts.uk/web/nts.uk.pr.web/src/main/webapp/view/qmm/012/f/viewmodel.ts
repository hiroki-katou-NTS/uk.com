module qmm012.f.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        //F_002
        checked_F_002: KnockoutObservable<boolean>;
        //F 001
        roundingRules_F_001: KnockoutObservableArray<any>;
        selectedRuleCode_F_001: any;
        constructor() {
            let self = this;
            self.enable = ko.observable(true);
            //F_005
            self.checked_F_002 = ko.observable(true);
            //F_001
            self.roundingRules_F_001 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
            ]);
            self.selectedRuleCode_F_001 = ko.observable(1);
        }
      
    }
}