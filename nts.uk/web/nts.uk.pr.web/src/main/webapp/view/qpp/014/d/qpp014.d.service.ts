module qpp014.d.service {
    var paths: any = {
        getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
    }

    export function getPaymentDateProcessingList(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getPaymentDateProcessingList);
    }
}



