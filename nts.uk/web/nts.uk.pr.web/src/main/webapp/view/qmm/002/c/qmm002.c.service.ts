module qmm002.c.service {
    var paths: any = {
        tranfer: "basic/system/bank/branch/tranfer",
        getBankList: "basic/system/bank/find/all"
    }

    export function tranferBranch(data): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax('com', paths.tranfer, data)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function getBankList(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getBankList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });
        return dfd.promise();
    }
}