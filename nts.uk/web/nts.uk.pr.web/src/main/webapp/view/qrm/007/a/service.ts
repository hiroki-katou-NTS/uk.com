module qrm007.a.service {
    var paths: any = {
        //getRetirementPayItemList: "pr/proto/paymentdatemaster/processing/findall",
        getRetirementPayItemList: "pr/core/retirement/payitem/findBycompanyCode",
        updateRetirementPayItem: "pr/core/retirement/payitem/update"
    }

    export function getRetirementPayItemList(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.getRetirementPayItemList)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function updateRetirementPayItem(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.updateRetirementPayItem, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
}