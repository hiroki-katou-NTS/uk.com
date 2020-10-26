module nts.uk.at.view.kwr006.c {
    export module service {
        const SLASH = '/'
        var paths = {
            getDataStartPage: "at/function/monthlyworkschedule/find",
            save: "at/function/monthlyworkschedule/save",
            remove: "at/function/monthlyworkschedule/delete",
            getEnumSettingPrint: "at/function/monthlyworkschedule/enumSettingPrint",
            getEnumRemarkInputContent: "at/function/monthlyworkschedule/enumRemarkInputContent",            
        }

        export function getDataStartPage(itemSelectionType : number): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage+ SLASH + itemSelectionType);
        }

        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.save, command);
        }

        export function remove(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.remove, command);
        }

        export function getEnumRemarkInputContent(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getEnumRemarkInputContent);
        }

    }
}