module qpp008.g.service {
    var paths: any = {
        getDetailDifferentials: "report/payment/comparing/confirm/getdiff/{0}/{1}"
    }

    export function getDetailDifferentials(processingYMEarlier: number, processingYMLater: number): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        let _path = nts.uk.text.format(paths.getDetailDifferentials, processingYMEarlier, processingYMLater);
        nts.uk.request.ajax(_path).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }
}