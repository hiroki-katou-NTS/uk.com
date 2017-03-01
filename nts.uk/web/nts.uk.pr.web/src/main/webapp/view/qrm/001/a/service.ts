module qrm001.a.service {
    var paths: any = {
        getRetirementPaymentList: "pr/core/retirement/payment/findByCompanyCodeandPersonId/{0}",
        register: "pr/core/retirement/payment/register",
        update: "pr/core/retirement/payment/update",
        remove: "pr/core/retirement/payment/remove"
    }
    
    export function getRetirementPaymentList(personId): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.getRetirementPaymentList, personId))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function registerRetirementPaymentInfo(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.register, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function updateRetirementPaymentInfo(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.update, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function removeRetirementPaymentInfo(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.remove, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}
