module qpp014.a.service {
    var path = {
        findPayDayProcessing: "pr/proto/paymentdata/banktransfer/findPayDayProcessing/{0}/{1}",
    };

    export function findAll(companyCode: string, payBonusAtr: number): JQueryPromise<any> {
        var _path = nts.uk.text.format(path.findPayDayProcessing, companyCode, payBonusAtr);
        return nts.uk.request.ajax(_path);
    }
}



