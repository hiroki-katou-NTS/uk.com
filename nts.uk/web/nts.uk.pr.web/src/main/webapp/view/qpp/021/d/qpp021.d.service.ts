module qpp021.d.service {

    var paths = {
        getRefundLayout: "report/payment/refundsetting/refundlayout/find/{0}",
        insertUpdateData: "report/payment/refundsetting/refundlayout/insertUpdateData",
    };

    export function getRefundLayout(printType: number): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let _path = nts.uk.text.format(paths.getRefundLayout, printType);
        nts.uk.request.ajax(_path).done(function(data: any) {
            dfd.resolve(data);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function insertUpdateData(data: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.insertUpdateData, ko.toJS(data)).done(function() {
            dfd.resolve();
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }
}
