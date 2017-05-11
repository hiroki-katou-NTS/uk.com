module qpp008.g.service {
    var paths: any = {
        insertUpdate: "report/payment/comparing/confirm/insertUpdate",
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