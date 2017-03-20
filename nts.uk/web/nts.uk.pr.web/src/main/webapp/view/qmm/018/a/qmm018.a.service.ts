module qmm018.a.service {
    var paths: any = {
        averagePayItemSelect: "pr/core/averagepay/findByCompanyCode",
        averagePayItemSelectBySalary: "pr/core/averagepay/findByItemSalary",
        averagePayItemSelectByAttend: "pr/core/averagepay/findByItemAttend",
        averagePayItemInsert: "pr/core/averagepay/register",
        averagePayItemUpdate: "pr/core/averagepay/update",
        itemSalaryUpdate: "",
        itemAttendUpdate: ""
    }
    
    export function averagePayItemSelect(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.averagePayItemSelect)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function averagePayItemSelectBySalary(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.averagePayItemSelectBySalary)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function averagePayItemSelectByAttend(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.averagePayItemSelectByAttend)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function averagePayItemInsert(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.averagePayItemInsert, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function averagePayItemUpdate(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.averagePayItemUpdate, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function itemSalaryUpdate(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.itemSalaryUpdate))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function itemAttendUpdate(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.itemAttendUpdate))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}