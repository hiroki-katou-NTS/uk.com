module qpp008.a.service {
    var paths: any = {
        getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall",
        saveAsPdf: "screen/pr/QPP008/saveAsPdf"
    }

    export function getPaymentDateProcessingList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getPaymentDateProcessingList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function saveAsPdf(): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdf);
    }
}