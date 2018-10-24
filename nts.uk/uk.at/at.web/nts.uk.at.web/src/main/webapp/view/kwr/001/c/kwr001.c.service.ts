module nts.uk.at.view.kwr001.c {
    export module service {
        
        const SLASH = "/";
        
        var paths = {
           getDataStartPage: "at/function/dailyworkschedule/find",
           save: "at/function/dailyworkschedule/save",
           remove: "at/function/dailyworkschedule/delete",
           getEnumName: "at/function/dailyworkschedule/enumName",
           getEnumRemarkContentChoice: "at/function/dailyworkschedule/enumRemarkContentChoice",
           getEnumRemarkInputContent: "at/function/dailyworkschedule/enumRemarkInputContent",
           findByCode: "at/function/dailyworkschedule/findByCode",
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
        
        export function getEnumName(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getEnumName);
        }
        
        export function getEnumRemarkContentChoice(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getEnumRemarkContentChoice);
        }
        
        export function getEnumRemarkInputContent(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getEnumRemarkInputContent);
        }
        
        export function findByCode(code: string): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.findByCode + SLASH + code);
        }
    }
}