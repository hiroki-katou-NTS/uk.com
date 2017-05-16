module kml001.shr.servicebase {
    var paths: any = {
        personCostCalculationSelect: "at/budget/premium/findBycompanyID",
        personCostCalculationInsert: "at/budget/premium/insert",
        personCostCalculationUpdate: "at/budget/premium/update",
        personCostCalculationDelete: "at/budget/premium/delete",
        premiumItemSelect: "at/budget/premium/premiumItem/findBycompanyID",
        premiumItemUpdate: "at/budget/premium/premiumItem/update"
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
    
    export function premiumItemSelect(): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.premiumItemSelect)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function premiumItemUpdate(command): JQueryPromise<any> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.premiumItemUpdate, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
}