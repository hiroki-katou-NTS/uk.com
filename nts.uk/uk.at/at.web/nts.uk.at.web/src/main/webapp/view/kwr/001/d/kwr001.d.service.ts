module nts.uk.at.view.kwr001.d {
    export module service {
        var paths = {
           getDataStartPage: "at/function/dailyworkschedule/findCopy",
        }
        
        export function getDataStartPage(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage);
        }
        
        const SLASH = "/";
    }
}