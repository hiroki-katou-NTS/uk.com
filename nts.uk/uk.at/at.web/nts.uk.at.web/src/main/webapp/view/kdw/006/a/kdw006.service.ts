module nts.uk.at.view.kdw006 {
    export module service {
        export class Service {
        }
        export function saveAsExcel(languageId: string): JQueryPromise<any> {
        return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "OperationSetting", domainType: "運用設定", languageId: languageId, reportType: 0});
    }
    }
}
