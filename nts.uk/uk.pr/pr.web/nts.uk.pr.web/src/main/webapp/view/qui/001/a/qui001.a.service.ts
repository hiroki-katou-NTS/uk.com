module nts.uk.pr.view.qui001.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            exportTxt: "export-txt-qui001",
            getEmpInsReportTxtSetting: "ctx/pr/report/printconfig/empinsurreportcreset/get-emp-ins-rpt-txt-stg",
            exportPDF: "export-pdf-qui001",
            getEmpInsReportSetting: "ctx/pr/report/printconfig/empinsurreportcreset/start"
        };

        export function getEmpInsReportTxtSetting(): JQueryPromise<any> {
            return ajax("pr", paths.getEmpInsReportTxtSetting);
        }

        export function getEmpInsReportSetting(): JQueryPromise<any> {
            return ajax("pr", paths.getEmpInsReportSetting);
        }

        export function exportTXT(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportTxt, data);
        }

        export function exportPDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportPDF, data);
        }
    }
}
