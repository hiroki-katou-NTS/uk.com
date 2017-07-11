module nts.uk.at.view.kmk005.e {
    export module service {
        var paths: any = {
            getListSetting: "at/share/bpTimeItemSetting/getListSetting",
            getListSpecialSetting: "at/share/bpTimeItemSetting/getListSpecialSetting",
            insertListSetting: "at/share/bpTimeItemSetting/insertListSetting",
            updateListSetting: "at/share/bpTimeItemSetting/updateListSetting"
        }
        
        export function getListSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListSetting);
        }
        
        export function getListSpecialSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListSpecialSetting);
        }
        
        export function insertListSetting(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.insertListSetting, command);
        }
        
        export function updateListSetting(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateListSetting, command);
        }
    }
}