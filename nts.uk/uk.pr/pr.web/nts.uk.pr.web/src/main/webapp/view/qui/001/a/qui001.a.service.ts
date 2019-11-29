module nts.uk.pr.view.qui001.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            exportCsv: "ctx/pr/report/printconfig/empinsreportsetting/export-csv-qui001",
            getEmpInsReportTxtSetting: "ctx/pr/report/printconfig/empinsurreportcreset/get-emp-ins-rpt-txt-stg",
            exportPDF: "ctx/pr/report/printconfig/empinsreportsetting/export-pdf-qui001",
            getEmpInsReportSetting: "ctx/pr/report/printconfig/empinsurreportcreset/start",

            addEmpInsRptTxtSetting: "ctx/pr/report/printconfig/empinsurreportcreset/add-emp-ins-rpt-txt-stg",
            updateEmpInsRptTxtSetting: "ctx/pr/report/printconfig/empinsurreportcreset/update-emp-ins-rpt-txt-stg"
        };

        export function getEmpInsReportTxtSetting(): JQueryPromise<any> {
            return ajax("pr", paths.getEmpInsReportTxtSetting);
        }

        export function getEmpInsReportSetting(): JQueryPromise<any> {
            return ajax("pr", paths.getEmpInsReportSetting);
        }

        export function exportCSV(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportCsv, data);
        }

        export function exportPDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportPDF, data);
        }

        export function addEmpInsRptTxtSetting(data): JQueryPromise<any> {
            return ajax('pr', paths.addEmpInsRptTxtSetting, data);
        }

        export function updateEmpInsRptTxtSetting(data): JQueryPromise<any> {
            return ajax('pr', paths.updateEmpInsRptTxtSetting, data);
        }
    }
}
