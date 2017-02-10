module nts.uk.pr.view.qet002.a {
    export module service {
        var servicePath = {
            printService: "/screen/pr/QET002/print",
        };

        export function print(query: any): JQueryPromise<qpp021.a.viewmodel.PaymentDataResultViewModel> {
            var dfd = $.Deferred<qet002.a.viewmodel.AccumulatedPaymentResultViewModel>();
            new nts.uk.ui.file.FileDownload("/screen/pr/QET002/print", query).print().done(function() {
                dfd.resolve();
            }).fail(function(res){
                dfd.reject(res);
            });

            return dfd.promise();
        }
    }
}