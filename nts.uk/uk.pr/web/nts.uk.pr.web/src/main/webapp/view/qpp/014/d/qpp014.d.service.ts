module qpp014.d.service {
    var path = {
        findDataScreenD: "pr/proto/payment/banktransfer/findDataScreenD/{0}",
    };

    export function findDataScreenD(processingNo: number): JQueryPromise<any> {
        var _path = nts.uk.text.format(path.findDataScreenD, processingNo);
        return nts.uk.request.ajax(_path);
    }
}



