module qrm007.a.service {
    var paths: any = {
        qremt_Retire_Pay_Item_SEL_1: "pr/core/retirement/payitem/findBycompanyCode",
        qremt_Retire_Pay_Item_UPD_1: "pr/core/retirement/payitem/update"
    }

    export function qremt_Retire_Pay_Item_SEL_1(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qremt_Retire_Pay_Item_SEL_1)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qremt_Retire_Pay_Item_UPD_1(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qremt_Retire_Pay_Item_UPD_1, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
}