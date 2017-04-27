module qpp008.g.service {
    var paths: any = {
        getDetailDifferentials: "report/payment/comparing/confirm/getdiff/{0}/{1}",
        insertUpdate: "report/payment/comparing/confirm/insertUpdate",
    }

    export function getDetailDifferentials(processingYMEarlier: number, processingYMLater: number, personIDs: Array<string>): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        let _path = nts.uk.text.format(paths.getDetailDifferentials, processingYMEarlier, processingYMLater);
        nts.uk.request.ajax(_path, personIDs).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function insertUpdatePaycompConfirm(insertUpdate: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.insertUpdate, insertUpdate).done(function() {
            dfd.resolve();
        }).fail(function(error: any) {
            dfd.reject(error);
        });
        return dfd.promise();
    }

}