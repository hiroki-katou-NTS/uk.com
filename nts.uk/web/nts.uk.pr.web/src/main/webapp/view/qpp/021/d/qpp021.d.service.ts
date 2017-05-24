module nts.uk.pr.view.qpp021.d {
    export module service {
        export class Service {
            paths = {
                getRefundLayout: "report/payment/refundsetting/refundlayout/find/{0}",
                insertUpdateData: "report/payment/refundsetting/refundlayout/insertUpdateData",
            };
            constructor() {}
            
            public getRefundLayout(printType: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getRefundLayout, printType);
                return nts.uk.request.ajax(_path);
            };

            public insertUpdateData(data: any): JQueryPromise<any> {
                return nts.uk.request.ajax(this.paths.insertUpdateData, ko.toJS(data));
            };
        }
    }
}
