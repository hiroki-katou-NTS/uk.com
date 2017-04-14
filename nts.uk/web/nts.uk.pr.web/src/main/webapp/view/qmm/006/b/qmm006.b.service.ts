module qmm006.b.service {
    var paths: any = {
        findBankAll: "basic/system/bank/find/all"
    }

    export function findBankAll(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.findBankAll)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}
