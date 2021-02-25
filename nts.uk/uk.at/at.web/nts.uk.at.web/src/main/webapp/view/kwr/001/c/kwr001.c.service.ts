module nts.uk.at.view.kwr001.c {
    export module service {
        
        const SLASH = "/";
        
        var paths = {
           getDataStartPage: "at/function/dailyworkschedule/find",
           save: "at/function/dailyworkschedule/save",
           remove: "at/function/dailyworkschedule/delete/{0}/{1}",
           getEnumName: "at/function/dailyworkschedule/enumName",
           getEnumRemarkContentChoice: "at/function/dailyworkschedule/enumRemarkContentChoice",
           getEnumRemarkInputContent: "at/function/dailyworkschedule/enumRemarkInputContent",
           findByCode: "at/function/dailyworkschedule/findByCode",
        }
        
        export function getDataStartPage(selectionType: number, code: string): JQueryPromise<any> {
            const dto = {
                selectTionType: selectionType,
                code: code
            };
            return nts.uk.request.ajax('at', paths.getDataStartPage, dto);
        }
        
        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.save, command);
        }
        
        export function remove(layoutId: string, selectionType: string): JQueryPromise<any> {
            let _path = nts.uk.text.format(paths.remove, layoutId, selectionType);
            return nts.uk.request.ajax('at', _path);
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
        
        export function findByCode(code: string, selectionType: number): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.findByCode + SLASH + code + SLASH + selectionType);
        }
    }
}