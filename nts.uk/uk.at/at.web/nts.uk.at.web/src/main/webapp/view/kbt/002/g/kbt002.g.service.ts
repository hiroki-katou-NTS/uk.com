module nts.uk.at.view.kbt002.g {
    export module service {
        var paths: any = {
            getEnumDataList: 'at/function/processexec/getEnum',
            getLogHistory: 'at/function/processexec/getLogHistory/',
        }
    
        export function getEnumDataList(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getEnumDataList);;
        }
        
        export function getLogHistory(execItemCd, execId): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getLogHistory + execItemCd + "/" + execId);
        }
    }
}