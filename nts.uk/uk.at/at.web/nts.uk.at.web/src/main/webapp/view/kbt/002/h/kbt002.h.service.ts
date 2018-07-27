module nts.uk.at.view.kbt002.h {
    export module service {
        var paths: any = {
            getProcExecList: 'at/function/processexec/getLogHistoryBySystemDates/',
            getLogHistory: 'at/function/processexec/getLogHistory/',
             findListDateRange: 'at/function/processexec/findListDateRange'
        }
        
        export function getProcExecList(execItemCd): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getProcExecList+execItemCd);
        }
         export function getLogHistory(execItemCd, execId): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getLogHistory + execItemCd + "/" + execId);
        }
         export function findListDateRange(Param:any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findListDateRange,Param);
        }
       
    }
}