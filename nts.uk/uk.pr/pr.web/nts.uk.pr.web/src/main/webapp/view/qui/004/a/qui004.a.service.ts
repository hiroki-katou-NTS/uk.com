module nts.uk.pr.view.qui004.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getReportSetting: "ctx/pr/report/printconfig/empinsurreportcreset/start",
            getReportTxtSetting: "ctx/pr/report/printconfig/empinsurreportcreset/get-emp-ins-rpt-txt-stg",
            registerReportSetting: " ",
            registerReportTxtSetting: " ",
            exportFilePDF: "ctx/pr/report/printconfig/empinsreportsetting/export-pdf-qui004",
            exportFileCSV: "ctx/pr/report/printconfig/empinsreportsetting/export-csv-qui004"
        };

        export function  getReportSetting() : JQueryPromise<any> {
            return ajax("pr", paths.getReportSetting);
        }

        export function  getReportTxtSetting() : JQueryPromise<any> {
            return ajax("pr", paths.getReportTxtSetting);
        }

        export function addSetting(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.registerReportSetting, data);
        }

        export function editSetting(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.registerReportTxtSetting, data);
        }

        export function exportFilePDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFilePDF, data);
        }

        export function exportFileCSV(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFileCSV, data);
        }

    }
}
