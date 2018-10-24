module nts.uk.at.view.kbt002.i {
    export module service {
        var paths: any = {
           findAppDataInfoDailyByExeID: "ctx/at/record/workrecord/appdatainfodaily/findallbyexecid",
           findAppDataInfoMonthlyByExeID: "ctx/at/record/workrecord/appdatainfomonthly/findallbyexecid",
           getLogHistory: 'at/function/processexec/getLogHistory/',
        }
    
        export function findAppDataInfoDailyByExeID(executionId : string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAppDataInfoDailyByExeID +"/"+executionId);;
        }
        
        export function findAppDataInfoMonthlyByExeID(executionId : string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAppDataInfoMonthlyByExeID +"/"+executionId);;
        }
        
        export function getLogHistory(execItemCd, execId): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getLogHistory + execItemCd + "/" + execId);
        }
    }
} 