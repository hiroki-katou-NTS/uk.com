module qpp008.b.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        /*checkBox*/
        checkedSel1: KnockoutObservable<boolean>;
        enableSel1: KnockoutObservable<boolean>;
        checkedSel2: KnockoutObservable<boolean>;
        enableSel2: KnockoutObservable<boolean>;
        checkedSel3: KnockoutObservable<boolean>;
        enableSel3: KnockoutObservable<boolean>;
        checkedSel4: KnockoutObservable<boolean>;
        enableSel4: KnockoutObservable<boolean>;
        /* SwictchButton*/
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        roundingRules1: KnockoutObservableArray<any>;
        selectedRuleCode1: any;
        departmentDate: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);

            /*checkBox*/
            self.checkedSel1 = ko.observable(true);
            self.enableSel1 = ko.observable(false);
            self.checkedSel2 = ko.observable(true);
            self.enableSel2 = ko.observable(true);
            self.checkedSel3 = ko.observable(true);
            self.enableSel3 = ko.observable(true);
            self.checkedSel4 = ko.observable(true);
            self.enableSel4 = ko.observable(true);
            self.departmentDate = ko.observable('2017/01/13' + 'の部門構成で集計します。')
            /*Switch*/
            self.roundingRules = ko.observableArray([
                { code: '1', name: '表示する' },
                { code: '2', name: '表示しない' },
            ]);
            self.selectedRuleCode1 = ko.observable(1);
            //B_SEL_011
            self.roundingRules1 = ko.observableArray([
                { code1: '1', name1: '表示する' },
                { code1: '2', name1: '表示しない' },
            ]);
            self.selectedRuleCode = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        
        closeDialog(): any {
            nts.uk.ui.windows.close();
        }
    }
}