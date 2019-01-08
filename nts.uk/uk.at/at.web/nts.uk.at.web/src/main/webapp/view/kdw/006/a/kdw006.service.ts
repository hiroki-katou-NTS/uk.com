module nts.uk.at.view.kdw006 {
    export module service {
        export class Service {
        }
        //common excel
        export function saveAsExcelCommon(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "OperationSetting", domainType: "KDW006"+nts.uk.resource.getText("KDW006_86"), languageId: languageId, reportType: 0});
        }
        //daily excel
        export function saveAsExcelDaily(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "DailyExport", domainType: "KDW006"+nts.uk.resource.getText("KDW006_86"), languageId: languageId, reportType: 0});
        }
        //Monthly excel
        export function saveAsExcelMonthly(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "MonthlyExport", domainType: "KDW006"+nts.uk.resource.getText("KDW006_86"), languageId: languageId, reportType: 0});
        }
    }
}
