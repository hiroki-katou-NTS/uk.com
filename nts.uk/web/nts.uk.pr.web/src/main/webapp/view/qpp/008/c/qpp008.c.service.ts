module qpp008.c.service {
    var paths: any = {
        getListComparingFormHeader: "report/payment/comparing/find/formHeader",
        getComparingFormForTab: "report/payment/comparing/getDataTab/{0}"
    }

    export function getListComparingFormHeader(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getListComparingFormHeader)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(error) {
                dfd.reject(error);
            })
        return dfd.promise();
    }

    export function getComparingFormForTab(formCode: string): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let _path = nts.uk.text.format(paths.getComparingFormForTab, formCode);
        nts.uk.request.ajax(_path).done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(error) {
                dfd.reject(error);
            })
        return dfd.promise();
    }


}