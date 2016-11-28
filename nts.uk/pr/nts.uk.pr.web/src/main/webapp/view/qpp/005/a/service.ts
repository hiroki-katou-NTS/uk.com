module nts.uk.pr.view.qpp005 {
    export module service {
        var servicePath = {
            getPaymentData: "/screen/pr/qpp005/paymentdata/find",
            insertData: "pr/proto/paymentdata/insert",
            updateData: "pr/proto/paymentdata/update"
        };

        export function getPaymentData(personId: string, employeeCode: string): JQueryPromise<qpp005.viewmodel.viewModel.PaymentDataResultViewModel> {
            var dfd = $.Deferred<qpp005.viewmodel.viewModel.PaymentDataResultViewModel>();
            var query = {
                personId: personId,
                employeeCode: employeeCode
            };
            nts.uk.request.ajax(servicePath.getPaymentData, query).done(function(res: qpp005.viewmodel.viewModel.PaymentDataResultViewModel) {
                dfd.resolve(res);
            }).fail(function(res) {
                alert('fail');
            });

            return dfd.promise();
        }
        
        export function register(paymentData: qpp005.viewmodel.viewModel.PaymentDataResultViewModel): any {
            var data = ko.toJS(paymentData);
            var isCreated = data.paymentHeader.isCreated;
            if(isCreated) {
                return nts.uk.request.ajax(servicePath.insertData, data);    
            }
            
            return nts.uk.request.ajax(servicePath.updateData, data);
        }
    }
}