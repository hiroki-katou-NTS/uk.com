module nts.uk.at.view.kmk005.k.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getWorkTime: "at/shared/worktimesetting/findAll",
        getWorkingTimesheetBonusPaySet: "at/share/wtBonusPaySetting/getListWTBonusPaySettingSetting",
        getWorkingTimesheetBonusPaySetByCode: "at/share/wtBonusPaySetting/getWTBPSetting/{0}",
        getBonusPaySettingByCode: "at/share/bonusPaySetting/getBonusPaySetting/{0}",
        saveSetting: "at/share/wtBonusPaySetting/saveSetting"
    }

    export function getWorkTime() {
        return ajax(paths.getWorkTime);
    }

    export function getWorkingTimesheetBonusPaySet() {
        return ajax(paths.getWorkingTimesheetBonusPaySet);
    }

    export function getWorkingTimesheetBonusPaySetByCode(code) {
        return ajax(format(paths.getWorkingTimesheetBonusPaySetByCode, code));
    }

    export function getBonusPaySettingByCode(code) {
        return ajax(format(paths.getBonusPaySettingByCode, code));
    }

    export function saveSetting(command) {
        if (!command.bonusPaySettingCode || command.bonusPaySettingCode == '000') {
            command.action = 0;
        }

        return ajax(paths.saveSetting, command);
    }
}