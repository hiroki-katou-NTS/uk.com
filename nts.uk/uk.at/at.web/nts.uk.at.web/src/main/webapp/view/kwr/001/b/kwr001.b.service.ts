module nts.uk.at.view.kwr001.b {
    export module service {
        var paths = {
           getErrorAlarmCode: "at/function/dailyworkschedule/getErrorAlarmCode"
        }
        
        export function getErrorAlarmCode(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getErrorAlarmCode);
        }
    }
}