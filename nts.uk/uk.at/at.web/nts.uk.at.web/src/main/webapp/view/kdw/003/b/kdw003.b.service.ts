module nts.uk.at.view.kdw003.b.service {
    var paths: any = {
        getErrorRefer: "screen/at/correctionofdailyperformance/getErrors",
        getApplication: "screen/at/correctionofdailyperformance/getApplication",
        exportCsv: "screen/at/correctionofdailyperformance/exportCsv",
        getErrAndAppTypeCd: "screen/at/correctionofdailyperformance/getErrAndAppTypeCd"
    }

    export function getErrorRefer(data: any) {
        return nts.uk.request.ajax(paths.getErrorRefer, data);
    }

    export function getApplication() {
        return nts.uk.request.ajax(paths.getApplication);
    }

    export function exportCsv(data: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportCsv, data);
    }
    
    export function getErrAndAppTypeCd(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getErrAndAppTypeCd, data);
    }
}