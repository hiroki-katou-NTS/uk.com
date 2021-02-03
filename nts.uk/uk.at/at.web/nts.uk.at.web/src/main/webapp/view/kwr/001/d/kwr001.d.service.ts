module nts.uk.at.view.kwr001.d {
    export module service {
        
        const SLASH = "/";
        
        var paths = {
            getDataStartPage: "at/function/dailyworkschedule/findCopy",
            executeCopy: "at/function/dailyworkschedule/executeCopy/{0}/{1}/{2}/{3}"
        }
        
        export function getDataStartPage(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage);
        }
        
        export function executeCopy(codeCopy: string, codeSourceSerivce: string, selectionType: number, fontSize: number): JQueryPromise<any> {
            let _path = nts.uk.text.format(paths.executeCopy, codeCopy, codeSourceSerivce, selectionType, fontSize);
            return nts.uk.request.ajax('at', _path);
        }
    }
}