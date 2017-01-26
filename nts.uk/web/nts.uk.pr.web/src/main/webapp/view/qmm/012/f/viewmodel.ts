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
                { code: '1', name: 'ゼロを表示する' },
                { code: '2', name: 'ゼロを表示しない' }
            ]);
            self.selectedRuleCode_F_001 = ko.observable(1);
        }
        start(): JQueryPromise<any> {
            var self = this;
            // Page load dfd.
            var dfd = $.Deferred();
            //dropdownlist event
        }
    }
}