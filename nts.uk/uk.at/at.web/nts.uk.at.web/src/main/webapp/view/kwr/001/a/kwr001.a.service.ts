module nts.uk.at.view.kwr001.a {
    export module service {
        var paths = {
           getDataStartPage: "at/function/dailyworkschedule/startPage"
        }
        
        export function getDataStartPage(isExist: boolean, keyRestore: string): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.getDataStartPage + SLASH + isExist + SLASH + keyRestore);
        }
        
        const SLASH = "/";
        
    }
}