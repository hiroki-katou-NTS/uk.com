module qpp014.a.service {
    var path = {
        findPayDayProcessing: "pr/proto/payment/banktransfer/findPayDayProcessing/{0}",
    };

    export function findAll(payBonusAtr: number): JQueryPromise<any> {
        var _path = nts.uk.text.format(path.findPayDayProcessing, payBonusAtr);
        return nts.uk.request.ajax(_path);
    }
}



