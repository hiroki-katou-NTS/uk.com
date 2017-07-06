module nts.uk.at.view.kmk005.k {
    export module service {
        var paths: any = {
            getWorkTime: "at/shared/worktime/findByCompanyID",
            getWorkingTimesheetBonusPaySet: "at/share/wtBonusPaySetting/getListWTBonusPaySettingSetting",
            getWorkingTimesheetBonusPaySetByCode: "at/share/wtBonusPaySetting/getWTBPSetting/{0}",
            getBonusPaySettingByCode: "at/share/bonusPaySetting/getBonusPaySetting/{0}",
            updateWorkingTimesheetBonusPaySet: "at/share/wtBonusPaySetting/updateWTBonusPaySettingSetting",
            insertWorkingTimesheetBonusPaySet: "at/share/wtBonusPaySetting/insertWTBonusPaySettingSetting",
            deleteWorkingTimesheetBonusPaySet: "at/share/wtBonusPaySetting/deleteWTBonusPaySettingSetting" 
        }
        
        export function getWorkTime(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getWorkTime);
        }
        
        export function getWorkingTimesheetBonusPaySet(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getWorkingTimesheetBonusPaySet);
        }
        
        export function getWorkingTimesheetBonusPaySetByCode(workingTimesheetCode): JQueryPromise<any> {
            return nts.uk.request.ajax(nts.uk.text.format(paths.getWorkingTimesheetBonusPaySetByCode, workingTimesheetCode));
        }
        
        export function getBonusPaySettingByCode(bonusPaySettingCode): JQueryPromise<any> {
            return nts.uk.request.ajax(nts.uk.text.format(paths.getBonusPaySettingByCode, bonusPaySettingCode));
        }
        
        export function updateWorkingTimesheetBonusPaySet(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateWorkingTimesheetBonusPaySet, command);
        }
        
        export function insertWorkingTimesheetBonusPaySet(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.insertWorkingTimesheetBonusPaySet, command);
        }
        
        export function deleteWorkingTimesheetBonusPaySet(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.deleteWorkingTimesheetBonusPaySet, command);
        }
    }
}