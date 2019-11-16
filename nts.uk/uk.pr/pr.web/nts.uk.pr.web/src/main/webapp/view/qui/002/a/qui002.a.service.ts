module nts.uk.pr.view.qui002.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getSocialInsurNotiCreateSet: "ctx/pr/report/printconfig/empinsurreportcreset/start",
            exportFilePDF: "ctx/pr/report/printconfig/empinsreportsetting/exportPDF"
        };

        export function start(): JQueryPromise<any> {
            return ajax("pr", paths.getSocialInsurNotiCreateSet);
        }

        export function exportFilePDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFilePDF, data);
        }
    }

}
