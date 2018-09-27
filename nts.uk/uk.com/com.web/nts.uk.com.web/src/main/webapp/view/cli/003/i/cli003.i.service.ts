module nts.uk.com.view.cli003.i {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        var paths = {
            getLogDisplaySettingByRecordType: "ctx/sys/log/app/get-log-display-setting-by-record-type"
        }

        export function getLogDisplaySettingByRecordType(paramInputLog): JQueryPromise<any> {         
            return ajax('com', paths.getLogDisplaySettingByRecordType,paramInputLog);
        };
   
    }
}
