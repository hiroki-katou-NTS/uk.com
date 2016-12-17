module nts.uk.pr.view.qpp021.a {
    export module service {
        var servicePath = {
            printService: "/file/paymentdata/print",
        };

        export function print(personId: string, employeeCode: string): JQueryPromise<qpp021.a.viewmodel.PaymentDataResultViewModel> {
            var dfd = $.Deferred<qpp005.a.viewmodel.PaymentDataResultViewModel>();
            var query = [{
                personId: personId,
                employeeCode: employeeCode
            }];
            new nts.uk.ui.file.FileDownload("/file/paymentdata/print", query).print();
//            nts.uk.request.ajax(servicePath.printService, query).done(function(res: qpp021.a.viewmodel.PaymentDataResultViewModel) {
                dfd.resolve();
//            }).fail(function(res) {
//               dfd.reject(res);
//            });

            return dfd.promise();
        }
    }
}