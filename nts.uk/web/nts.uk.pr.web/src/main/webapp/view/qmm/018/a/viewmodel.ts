module qmm018.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.roundingRules = ko.observableArray([
                { code: '1', name: 'left' },
                { code: '2', name: 'right' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
    }
}