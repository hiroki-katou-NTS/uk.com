module qpp004.l.service {
    var paths = {
        createpaymentdata: "pr/proto/paymentdate/add"
    };
    
    /**
     * Get list payment date processing.
     */
    export function createPaymentData(param: any): JQueryPromise<any> {
        var dfd = $.Deferred<any>();

        nts.uk.request.ajax(paths.createpaymentdata, param)
            .done(function(res: any){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    
   
}