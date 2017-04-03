module qpp008.b.service {
    var paths: any = {
        getComparingPrintSet: "report/payment/comparing/setting/find",
        insertData: "report/payment/comparing/setting/insertData",
        updateData: "report/payment/comparing/setting/updateData",
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

    export function insertUpdateData(isUpdate: any, inserUpdateData: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let _path = isUpdate ? paths.updateData : paths.insertData;
        nts.uk.request.ajax(_path, inserUpdateData).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function deleteData(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.deleteData).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }
}