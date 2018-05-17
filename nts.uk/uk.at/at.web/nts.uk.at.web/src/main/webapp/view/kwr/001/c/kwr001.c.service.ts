module nts.uk.at.view.kwr001.c {
    export module service {
        var paths = {
           getDataStartPage: "at/function/dailyworkschedule/find",
           save: "at/function/dailyworkschedule/save",
           remove: "at/function/dailyworkschedule/delete",
        }
        
        export function getDataStartPage(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage);
        }
        
        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.save, command);
        }
        
        export function remove(code: string): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.remove + SLASH + code);
        }
        
        const SLASH = "/";
    }
}