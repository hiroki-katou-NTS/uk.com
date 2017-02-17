module qmm006.c.service {
    var paths: any = {
        findAll: "basic/system/bank/linebank/findAll"
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
}