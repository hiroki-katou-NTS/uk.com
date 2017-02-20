module qmm018.a.service {
    var paths: any = {
        getAveragePay: "pr/core/averagepay/findByCompanyCode",
        registerAveragePay: "pr/core/averagepay/register",
        updateAveragePay: "pr/core/averagepay/update",
        getItem: "pr/proto/item/findall/bycategory/{0}"
    }
    
    export function getAveragePay(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.getAveragePay)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function registerAveragePay(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.registerAveragePay, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function updateAveragePay(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.updateAveragePay, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function getItem(categoryAtr): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.getItem, categoryAtr))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}