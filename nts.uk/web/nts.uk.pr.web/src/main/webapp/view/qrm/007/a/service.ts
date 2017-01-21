module qrm007.a.service {
    var paths: any = {
        getRetirementPayItemList: "pr/proto/paymentdatemaster/processing/findall",
        //getRetirementPayItemList: "pr/core/retirement/payitem/findAll",
        //updateData: "pr/core/retirement/payitem/update"
    }

    export function getRetirementPayItemList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getRetirementPayItemList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    /*
    export function updateData(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.updateData)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    */
}