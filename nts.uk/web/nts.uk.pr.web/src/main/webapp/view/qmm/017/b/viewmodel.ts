
module nts.qmm017 {
    export class BScreen {        
        yearMonth: any;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode:  KnockoutObservable<any>;
        roundingRules2: KnockoutObservableArray<any>;
        selectedRuleCode2:  KnockoutObservable<any>;

        constructor() {
            var self = this;
                        
            self.roundingRules = ko.observableArray([
                { code: '0', name: 'かんたん設定' },
                { code: '1', name: '詳細設定' }
            ]);
            self.selectedRuleCode = ko.observable(0);
            self.roundingRules2 = ko.observableArray([
                { code: '0', name: '利用しない' },
                { code: '1', name: '利用する' }
            ]);
            self.selectedRuleCode2 = ko.observable(0);
            
            self.yearMonth = ko.observable('2016/12');
        }
    }
}

