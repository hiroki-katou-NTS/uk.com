module qmm006.c.service {
    var paths: any = {
        findAll: "basic/system/bank/linebank/findAll",
        transfer: "basic/system/bank/linebank/transfer"
    }
    export function findAll(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.findAll)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function transfer(data): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax('com', paths.transfer, data)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}