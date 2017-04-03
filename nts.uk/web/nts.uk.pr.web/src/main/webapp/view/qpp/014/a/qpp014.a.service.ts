module qpp014.a.service {
    var path = {
        findPayDayProcessing: "pr/proto/paymentdata/banktransfer/findPayDayProcessing/{companyCode}/{pay_bonus_atr}",
    };
    
    export function findAll(): JQueryPromise<any> {
        return nts.uk.request.ajax(path.findPayDayProcessing);
    }
}



