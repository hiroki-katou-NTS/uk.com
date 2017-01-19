module qmm018.a.service {
    var paths: any = {
        //getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall",
        saveData: "pr/core/avepay/register"
    }
    
    /*
    export function getPaymentDateProcessingList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getPaymentDateProcessingList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    */
    
    export function saveData(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.saveData, command)
            .done(function(res: any) {
                console.log(res);
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}