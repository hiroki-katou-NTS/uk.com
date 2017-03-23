
module nts.qmm017 {
    export class BScreen {
        startYearMonth: any;
        roundingRules: KnockoutObservableArray<any>;
        selectedDifficultyAtr: KnockoutObservable<any>;
        roundingRules2: KnockoutObservableArray<any>;
        selectedConditionAtr: KnockoutObservable<any>;
        formulaCode: KnockoutObservable<string>;
        formulaName: KnockoutObservable<string>;
        startYearMonthFormated: KnockoutObservable<string>;
        comboBoxUseMaster: KnockoutObservable<any>;
        isNewMode: KnockoutObservable<boolean>;

        constructor(data) {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '0', name: 'かんたん設定' },
                { code: '1', name: '詳細設定' }
            ]);
            self.selectedDifficultyAtr = ko.observable(0);
            self.roundingRules2 = ko.observableArray([
                { code: '0', name: '利用しない' },
                { code: '1', name: '利用する' }
            ]);
            self.selectedConditionAtr = ko.observable(0);
            self.startYearMonth = ko.observable('');
            self.formulaCode = ko.observable('');
            self.formulaName = ko.observable('');
            self.startYearMonthFormated = ko.observable('');
            self.isNewMode = ko.observable(data.isNewMode());;
            data.isNewMode.subscribe(function(val){
                self.isNewMode(val);
            });
            self.startYearMonth.subscribe(function(ymChange) {
                if (!self.isNewMode()) {
                    self.startYearMonthFormated('(' + nts.uk.time.yearmonthInJapanEmpire(ymChange).toString() + ') ~');
                } else {
                    self.startYearMonthFormated = ko.observable('');
                }
            });
            self.comboBoxUseMaster = ko.observable({
                itemList: ko.observableArray([
                    {code: '1', name: '雇用マスタ'},
                    {code: '2', name: '部門マスタ'},
                    {code: '3', name: '分類マスタ'},
                    {code: '4', name: '給与分類マスタ'},
                    {code: '5', name: '職位マスタ'},
                    {code: '6', name: '給与区分'},
                ]),
                selectedCode: ko.observable('')
            });
        }
    }
}

