module nts.uk.at.view.kmk005.f {
    export module service {
        var paths: any = {
            getBonusPaySetting: "at/share/bonusPaySetting/getAllBonusPaySetting",
            insertBonusPaySetting: "at/share/bonusPaySetting/addBonusPaySetting",
            updateBonusPaySetting: "at/share/bonusPaySetting/updateBonusPaySetting",
            deleteBonusPaySetting: "at/share/bonusPaySetting/removeBonusPaySetting",
            getBonusPayTimeItem: "at/share/bonusPayTimeItem/getListBonusPayTimeItemInUse",
            getSpecBonusPayTimeItem: "at/share/bonusPayTimeItem/getListSpecialBonusPayTimeItemInUse",
            getSpecDateItem: "at/schedule/shift/businesscalendar/specificdate/getallspecificdate",
            getBonusPayTimesheet: "at/share/bpTimesheet/getListTimesheet/{0}",
            getSpecBonusPayTimesheet: "at/share/specBonusPayTimesheet/getListTimesheet/{0}"
        }
        
        export function getBonusPaySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getBonusPaySetting);
        }
        
        export function insertBonusPaySetting(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.insertBonusPaySetting, command);
        }
        
        export function updateBonusPaySetting(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateBonusPaySetting, command);
        }
        
        export function deleteBonusPaySetting(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.deleteBonusPaySetting, command);
        }
        
        export function getBonusPayTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getBonusPayTimeItem);
        }
        
        export function getSpecBonusPayTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getSpecBonusPayTimeItem);
        }
        
        export function getSpecDateItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getSpecDateItem);
        }
        
        export function getBonusPayTimesheet(bonusPaySetingCode): JQueryPromise<any> {
            return nts.uk.request.ajax(nts.uk.text.format(paths.getBonusPayTimesheet, bonusPaySetingCode));
        }
        
        export function getSpecBonusPayTimesheet(bonusPaySetingCode): JQueryPromise<any> {
            return nts.uk.request.ajax(nts.uk.text.format(paths.getSpecBonusPayTimesheet, bonusPaySetingCode));
        }
    }
}