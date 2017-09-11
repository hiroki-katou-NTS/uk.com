module nts.uk.at.view.kmk002.a {
    export module viewmodel {

        export class ScreenModel {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            selectedCbb: any;
            checked: any;
            columns: any;
            selectedCode: KnockoutObservable<any>;
            name: KnockoutObservable<any>;

            constructor() {
                var self = this;
                self.roundingRules = ko.observableArray([
                    { code: '1', name: '四捨五入' },
                    { code: '2', name: '切り上げ' }
                ]);
                self.selectedRuleCode = ko.observable(1);
                self.selectedCbb = ko.observable();
                self.checked = ko.observable();
                self.selectedCode = ko.observable();
                self.name = ko.observable();
                self.columns = ko.observableArray([
                    { headerText: 'code', key: 'code', width: 100 },
                    { headerText: 'name', key: 'name', width: 150 },
                ]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
        }

        interface OptionalItem {
            optionalItemNo: string;
            optionalItemName: string;
            optionalItemAtr: number;
            usageClassification: number;
            empConditionClassification: number;
            performanceClassification: number;
            calculationResultRange: CalculationResultRange;
        }
        interface CalculationResultRange {
            upperLimit: boolean;
            lowerLimit: boolean;
            numberRange: number;
            timeRange: number;
            amountRange: number;
        }
        interface OptionalItemFormula {
            formulaId: string;
            formulaName: string;
            optionalItemNo: string;
            formulaAtr: number;
            symbolValue: string;
            formulaSetting: any;
            monthlyRounding: any;
            dailyRounding: any;
        }
    }
}