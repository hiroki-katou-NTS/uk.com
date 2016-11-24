module nts.uk.pr.view.qpp._005 {

    export module viewmodel {
        export class ScreenModel {
            isHandInput: KnockoutObservable<boolean>;
            paymentDataResult: KnockoutObservable<service.model.PaymentDataResult>;

            constructor() {
                var self = this;
                self.isHandInput = ko.observable(true);
                self.paymentDataResult = ko.observable<service.model.PaymentDataResult>();
            }
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                service.getPaymentData("1", "1").done(function(paymentResult: service.model.PaymentDataResult) {
                    self.paymentDataResult(paymentResult);
                    dfd.resolve(null);
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            }

            public openEmployeeList() {
                nts.uk.ui.windows.sub.modal('/view/qpp/005/dlgemployeelist/index.xhtml');
            }
        };

    }
}