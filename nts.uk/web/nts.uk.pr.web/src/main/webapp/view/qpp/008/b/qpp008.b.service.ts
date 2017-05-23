module qpp008.b.service {
    var paths: any = {
        getComparingPrintSet: "report/payment/comparing/setting/find",
        inserUpdatetData: "report/payment/comparing/setting/insertUpdateData",
        deleteData: "report/payment/comparing/setting/deleteData",

    }

    export function getComparingPrintSet(): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.getComparingPrintSet).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function insertUpdateData(inserUpdateData: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.inserUpdatetData, inserUpdateData).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function deleteData(): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.deleteData).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }
}