module qmm006.a.service {
    var paths: any = {
        saveData: "basic/system/bank/linebank/add",
        findAll: "basic/system/bank/linebank/findAll",
        update: "basic/system/bank/linebank/update",
        remove: "basic/system/bank/linebank/remove",
        findBankAll: "basic/system/bank/find/all"
    }

    export function remove(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.remove, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function saveData(isCreated, command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        var path = isCreated ? paths.saveData : paths.update;
        nts.uk.request.ajax("com", path, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
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
