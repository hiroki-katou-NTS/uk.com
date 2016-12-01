module nts.uk.pr.view.qpp005 {
    export module service {
        var servicePath = {
            getPaymentData: "/screen/pr/qpp005/paymentdata/find",
            insertData: "pr/proto/paymentdata/insert",
            updateData: "pr/proto/paymentdata/update"
        };

        export function getPaymentData(personId: string, employeeCode: string): JQueryPromise<qpp005.viewmodel.PaymentDataResultViewModel> {
            var dfd = $.Deferred<qpp005.viewmodel.PaymentDataResultViewModel>();
            var query = {
                personId: personId,
                employeeCode: employeeCode
            };
            nts.uk.request.ajax(servicePath.getPaymentData, query).done(function(res: qpp005.viewmodel.PaymentDataResultViewModel) {
                dfd.resolve(res);
            }).fail(function(res) {
               dfd.reject(res);
            });

            return dfd.promise();
        }

        export function register(paymentData: qpp005.viewmodel.PaymentDataResultViewModel): any {
            var result = {
                paymentHeader: ko.toJS(paymentData.paymentHeader),
                categories: ko.toJS(paymentData.categories)
            };
            var isCreated = result.paymentHeader.created;
            if (!isCreated) {
                return nts.uk.request.ajax(servicePath.insertData, result);
            }

            return nts.uk.request.ajax(servicePath.updateData, result);
        }
    }
}