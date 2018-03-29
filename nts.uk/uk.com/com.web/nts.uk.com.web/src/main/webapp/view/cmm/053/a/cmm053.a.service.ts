module nts.uk.com.view.cmm053.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getSettingManager: "workflow/approvermanagement/workroot/find/settingOfManager/{0}",
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWkp",
        getEmployeeByCode: "workflow/approvermanagement/workroot/find/getEmployeeByCode/{0}",
        getPastHistory: "workflow/approvermanagement/workroot/find/settingOfManager/getPastHistory/{0}",
        updateHistoryByManagerSetting: "workflow/approvermanagement/workroot/updateHistoryByManagerSetting"
    }

    export function getSettingManager(employeeId: string): JQueryPromise<any> {
        return ajax(format(paths.getSettingManager, employeeId));
    }

    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax(paths.getInfoEmLogin);
    }

    export function getWpName(): JQueryPromise<any> {
        return ajax(paths.getWpName);
    }

    export function getEmployeeByCode(employeeCode: string): JQueryPromise<any> {
        return ajax(format(paths.getEmployeeByCode, employeeCode));
    }

    export function getPastHistory(employeeId: string): JQueryPromise<any> {
        return ajax(format(paths.getPastHistory, employeeId));
    }

    export function updateHistoryByManagerSetting(command): JQueryPromise<any> {
        return ajax(paths.updateHistoryByManagerSetting, command);
    }
}