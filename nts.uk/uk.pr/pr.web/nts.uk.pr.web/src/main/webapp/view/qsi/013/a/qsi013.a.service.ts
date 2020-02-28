module nts.uk.pr.view.qsi013.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getSocialInsurNotiCreateSet: "ctx/pr/report/printconfig/socinsurnoticreset/getSocialInsurNotiCreateSet",
            exportFilePDF: "ctx/pr/file/printconfig/notice/exportFilePDF",
            exportFileCSV: "ctx/pr/file/printconfig/notice/exportFileCSV"
        };

        export function getSocialInsurNotiCreateSet(): JQueryPromise<any> {
            return ajax("pr", paths.getSocialInsurNotiCreateSet);
        }

        export function exportFilePDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFilePDF, data);
        }

        export function exportFileCSV(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFileCSV, data);
        }

    }

}
