module qet002.a {
    export module service {
        var servicePath = {
            printService: "/screen/pr/qet002/generate",
        };

        export function printService(query: any): JQueryPromise<qet002.a.viewmodel.AccumulatedPaymentResultViewModel> {
            var dfd = $.Deferred<qet002.a.viewmodel.AccumulatedPaymentResultViewModel>();
            //            new nts.uk.ui.file.FileDownload("/screen/pr/qet002/generate", query).print().done(function() {
            //                dfd.resolve();
            //            }).fail(function(res){
            //                dfd.reject(res);
            //            });
            //            
            //            nts.uk.request.exportFile('/screen/pr/qet002/generate', { value: 'abc' }).done(() => {
            //                console.log('DONE!!');
            //            });
            nts.uk.request.exportFile(servicePath.printService, { value: 'abc' }).done(function() {
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }
    }
}

