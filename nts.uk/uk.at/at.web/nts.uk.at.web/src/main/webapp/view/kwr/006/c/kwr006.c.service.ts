module nts.uk.at.view.kwr006.c {
    export module service {
        const SLASH = "/";

        var paths = {
            getDataStartPage: "at/function/monthlyworkschedule/find",
            save: "at/function/monthlyworkschedule/save",
            remove: "at/function/monthlyworkschedule/delete",
            getEnumSettingPrint: "at/function/monthlyworkschedule/enumSettingPrint",
            getEnumRemarkInputContent: "at/function/monthlyworkschedule/enumRemarkInputContent",            
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

        export function getEnumSettingPrint(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getEnumSettingPrint);
        }
        
        export function getEnumRemarkInputContent(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getEnumRemarkInputContent);
        }

    }
}