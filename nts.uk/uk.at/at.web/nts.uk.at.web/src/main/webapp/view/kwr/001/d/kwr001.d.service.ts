module nts.uk.at.view.kwr001.d {
    export module service {
        
        const SLASH = "/";
        
        var paths = {
            getDataStartPage: "at/function/dailyworkschedule/findCopy",
            executeCopy: "at/function/dailyworkschedule/executeCopy"
        }
        
        export function getDataStartPage(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage);
        }
        
        export function executeCopy(codeCopy: string, codeSourceSerivce: string): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.executeCopy + SLASH + codeCopy + SLASH + codeSourceSerivce);
        }
    }
}