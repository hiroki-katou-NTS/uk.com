module nts.uk.com.view.cli003.f {
    import ajax = nts.uk.request.ajax;

    export module service {

        var paths = {
            getLogOutputItemsByRecordTypeItemNos: "ctx/sys/log/app/get-log-output-item-by-record-type-item-no-list",
            getLogBasicInfoByModifyDate: "ctx/sys/log/record-reference/get-log-basic-info-by-modify-date",
            logSettingExportCsv: "ctx/sys/log/record-reference/export-csv",
            getLogSettingsBySystem: "sys/portal/logsettings/findBySystem",
            getLogDataSave: "ctx/sys/assist/app/findResultOfSaving",
            getLogDataRecover: "ctx/sys/assist/app/findResultOfRestoration",
            getLogDataDelete: "ctx/sys/assist/app/findResultOfRestoration",
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
        export function getLogSettingsBySystem(systemType : number): JQueryPromise<any> {
            return ajax(`${paths.getLogSettingsBySystem}/${systemType}`);
        };
        export function getLogDataSave(logDataParams): JQueryPromise<any> {
            return ajax('com', paths.getLogDataSave, logDataParams);
        };
        export function getLogDataRecover(logDataParams): JQueryPromise<any> {
            return ajax('com', paths.getLogDataRecover, logDataParams);
        };
        export function getLogDataDelete(logDataParams): JQueryPromise<any> {
            return ajax('com', paths.getLogDataDelete, logDataParams);
        };
    }
}
