module qpp008.a.service {
    var paths = {
        saveAsPdf: "screen/pr/QPP008/saveAsPdf",
        getListComparingFormHeader: "report/payment/comparing/find/formHeader",
        getDetailDifferentials: "report/payment/comparing/confirm/getdiff/{0}/{1}",
        getPersonInfo: "report/payment/comparing/masterpage/getpersons",
    }

    export function saveAsPdf(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdf, command);
    }

    export function getListComparingFormHeader(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getListComparingFormHeader).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function getDetailDifferentials(processingYMEarlier: number, processingYMLater: number, personIDs: Array<string>): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        let _path = nts.uk.text.format(paths.getDetailDifferentials, processingYMEarlier, processingYMLater);
        nts.uk.request.ajax(_path, personIDs).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function getPersonInfo(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getPersonInfo).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }
}