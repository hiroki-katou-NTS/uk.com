module nts.uk.at.view.kmk005.e {
    export module service {
        var paths: any = {
            getListBonusPayTimeItem: "at/share/bonusPayTimeItem/getListBonusPayTimeItem",
            getListSpecialBonusPayTimeItem: "at/share/bonusPayTimeItem/getListSpecialBonusPayTimeItem",
            getListSetting: "at/share/bpTimeItemSetting/getListSetting",
            getListSpecialSetting: "at/share/bpTimeItemSetting/getListSpecialSetting",
            insertListSetting: "at/share/bpTimeItemSetting/addListSetting",
            updateListSetting: "at/share/bpTimeItemSetting/updateListSetting"
        }
        
        export function getListBonusPayTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListBonusPayTimeItem);
        }
        
        export function getListSpecialBonusPayTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListSpecialBonusPayTimeItem);
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