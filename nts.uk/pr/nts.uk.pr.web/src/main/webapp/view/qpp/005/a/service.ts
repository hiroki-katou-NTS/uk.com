module nts.uk.pr.view.qpp005 {
    export module service {
        var servicePath = {
            getPaymentData: "/screen/pr/qpp005/paymentdata/find"
        };

        export function getPaymentData(personId: string, employeeCode: string): JQueryPromise<qpp005.viewmodel.model.PaymentDataResult> {
            var dfd = $.Deferred<qpp005.viewmodel.model.PaymentDataResult>();
            var query = {
                personId: personId,
                employeeCode: employeeCode
            };
            nts.uk.request.ajax(servicePath.getPaymentData, query).done(function(res: any) {
                var paymentResult: qpp005.viewmodel.model.PaymentDataResult = res;
                dfd.resolve(paymentResult);
            }).fail(function(res) {
                alert('fail');
            });

            return dfd.promise();
        }
    }
}