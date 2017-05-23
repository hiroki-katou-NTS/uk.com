module qpp021.d.service {

    var paths = {
        getRefundLayout: "report/payment/refundsetting/refundlayout/find/{0}",
        insertUpdateData: "report/payment/refundsetting/refundlayout/insertUpdateData",
    };

    export function getRefundLayout(printType: number): JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getRefundLayout, printType);
        return nts.uk.request.ajax(_path);
    }

    export function insertUpdateData(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.insertUpdateData, ko.toJS(data));
    }
}
