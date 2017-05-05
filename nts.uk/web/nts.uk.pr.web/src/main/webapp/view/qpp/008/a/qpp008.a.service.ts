module qpp008.a.service {
    var paths = {
        saveAsPdf: "screen/pr/QPP008/saveAsPdf",
        getListComparingFormHeader: "report/payment/comparing/find/formHeader",

    }
    
    export function saveAsPdf(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdf, command);
    }

    export function getListComparingFormHeader(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getListComparingFormHeader)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            }).fail(function(error: any) {
                dfd.reject(error);
            })
        return dfd.promise();
    }
}