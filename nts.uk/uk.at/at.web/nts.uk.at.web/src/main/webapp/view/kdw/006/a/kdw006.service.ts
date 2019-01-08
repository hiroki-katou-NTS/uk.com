module nts.uk.at.view.kdw006 {
    export module service {
        export class Service {
        }
        //common excel
        export function saveAsExcelCommon(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "OperationSetting", domainType: "KDW006"+__viewContext.program.programName, languageId: languageId, reportType: 0});
        }
    }
}
