module qmm006.a.service {
    var paths: any = {
        saveData: "basic/system/bank/linebank/add",
        findAll: "basic/system/bank/linebank/findAll",
        update: "basic/system/bank/linebank/update",
        remove: "basic/system/bank/linebank/remove",
        findBankAll: "basic/system/bank/find/all",
        checkExistBankAndBranch: "basic/system/bank/find/check"
    }

    /**
     * remove data (lineBank) in database
     */
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

    /**
     * update or insert data (lineBank) in database
     * define update mode or insert mode base-on isEnable property
     */
    export function saveData(isEnable, command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        var path = isEnable ? paths.saveData : paths.update;
        nts.uk.request.ajax("com", path, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
     * get data from database to screen
     */
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

    /**
     * get data of Bank 
     */
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

    /**
     * check exist data of Bank
     */
    export function checkExistBankAndBranch(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.checkExistBankAndBranch)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}
