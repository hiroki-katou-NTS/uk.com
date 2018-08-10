module nts.uk.com.view.cli003.b {
    import ajax = nts.uk.request.ajax;

    export module service {

        var paths = {
            getLogOutputItemsByRecordTypeItemNos: "ctx/sys/log/app/get-log-output-item-by-record-type-item-no-list",
            getLogBasicInfoByModifyDate: "ctx/sys/log/record-reference/get-log-basic-info-by-modify-date",
            logSettingExportCsv: "ctx/sys/log/record-reference/export-csv",
            getLogDisplaySettingByCode: "ctx/sys/log/app/get-log-display-setting-by-code",
            getLogDisplaySettingByCodeAndFlag: "ctx/sys/log/app/get-log-display-setting-by-code-flag",
            logSettingExportCsvScreenI: "ctx/sys/log/record-reference/export-csv-screeni",
            getLogBasicInfoDataByModifyDate: "ctx/sys/log/record-reference/get-log-basic-info-data-by-date",
            getLogOutputItemsByRecordTypeItemNosAll: "ctx/sys/log/app/get-log-output-item-by-record-type-item-no-list-all",
            filterLogDataExport: "ctx/sys/log/record-reference/filterLogData"
        }

        export function getLogOutputItemsByRecordTypeItemNos(paramOutputItem): JQueryPromise<any> {
            return ajax('com', paths.getLogOutputItemsByRecordTypeItemNos, paramOutputItem);
        };
        
        export function getLogBasicInfoByModifyDate(paramLog): JQueryPromise<any> {
            return ajax('com', paths.getLogBasicInfoByModifyDate, paramLog);
        };
        
        export function logSettingExportCsv(params): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.logSettingExportCsv, params);
        };
        export function getLogDisplaySettingByCode(code: string): JQueryPromise<any> {         
            return ajax('com', paths.getLogDisplaySettingByCode, code);
        };
         export function getLogDisplaySettingByCodeAndFlag(code: string): JQueryPromise<any> {         
            return ajax('com', paths.getLogDisplaySettingByCodeAndFlag, code);
        };
        export function logSettingExportCsvScreenI(params): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.logSettingExportCsvScreenI, params);
        };
        export function getLogBasicInfoDataByModifyDate(paramLog): JQueryPromise<any> {
            return ajax('com', paths.getLogBasicInfoDataByModifyDate, paramLog);
        };
        export function getLogOutputItemsByRecordTypeItemNosAll(paramOutputItem): JQueryPromise<any> {
            return ajax('com', paths.getLogOutputItemsByRecordTypeItemNosAll, paramOutputItem);
        };
          export function filterLogDataExport(params): JQueryPromise<any> {
            return ajax('com', paths.filterLogDataExport, params);
        };
    }
}
