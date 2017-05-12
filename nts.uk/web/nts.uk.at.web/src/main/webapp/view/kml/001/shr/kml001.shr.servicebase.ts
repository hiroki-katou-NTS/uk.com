module kml001.shr.servicebase {
    var paths: any = {
        personCostCalculationSelect: "at/budget/premium/findBycompanyID",
        personCostCalculationInsert: "at/budget/premium/insert",
        personCostCalculationUpdate: "at/budget/premium/update",
        personCostCalculationDelete: "at/budget/premium/delete",
        extraTimeSelect: "at/budget/premium/extraTime/findBycompanyID",
        extraTimeUpdate: "at/budget/premium/extraTime/update"
    }
    
    export function personCostCalculationSelect(): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.personCostCalculationSelect)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
   
    export function personCostCalculationInsert(command): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.personCostCalculationInsert, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function personCostCalculationUpdate(command): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.personCostCalculationUpdate, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function personCostCalculationDelete(command): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.personCostCalculationDelete, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function extraTimeSelect(): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.extraTimeSelect)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function extraTimeUpdate(command): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.extraTimeUpdate, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
}