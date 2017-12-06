module cps002.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let
        regpath = "ctx/pereg/",
        paths: any = {
            getEmployeeCode: 'basic/organization/employee/getGenerateEmplCode',
            getCardNumber: 'basic/organization/employee/getGenerateCardNo',
            getLayout: 'person/newlayout/get',
            getAllInitValueSetting: 'person/info/setting/init/findAllHasChild',
            getSelfRoleAuth: 'roles/auth/get-self-auth',
            getUserSetting: 'usersetting/getUserSetting',
            getLastRegHistory: 'empreghistory/getLastRegHistory',
            getGenerateEmplCodeAndComId: 'addemployee/getGenerateEmplCodeAndComId',
            validateEmpInfo: 'addemployee/validateEmpInfo',
            getCopySetting: 'copysetting/setting/getCopySetting',
            getAllCopySettingItem: 'copysetting/item/getAll/{0}/{1}/{2}',
            getAllInitValueCtgSetting: 'initsetting/category/findAllBySetId/{0}',
            getAllInitValueItemSetting: 'initsetting/item/findInit/{0}/{1}/{2}',
            getLayoutByCreateType: 'layout/getByCreateType',
            addNewEmployee: 'addemployee/addNewEmployee'
        };

    export function getLayout() {
        return ajax(regpath + paths.getLayout);
    }

    export function getUserSetting() {
        return ajax(regpath + paths.getUserSetting);
    }

    export function getLastRegHistory() {
        return ajax(regpath + paths.getLastRegHistory);
    }

    export function getEmployeeCode(employeeLetter) {
        return ajax(paths.getEmployeeCode, employeeLetter);
    }

    export function getCardNumber(cardLetter) {
        return ajax(paths.getCardNumber, cardLetter);
    }

    export function getEmployeeCodeAndComId(employeeLetter) {
        return ajax(paths.getCardNumber, employeeLetter);
    }

    export function validateEmpInfo(command) {
        return ajax(regpath + paths.validateEmpInfo, command);
    }

    export function getCopySetting() {
        return ajax(regpath + paths.getCopySetting);
    }

    export function getAllCopySettingItem(employeeId, categoryCd, baseDate) {
        return ajax(format(regpath + paths.getAllCopySettingItem, employeeId, categoryCd, baseDate));
    }

    export function getAllInitValueSetting() {
        return ajax(regpath + paths.getAllInitValueSetting);
    }

    export function getAllInitValueCtgSetting(settingId: string) {
        return ajax(format(regpath + paths.getAllInitValueCtgSetting, settingId));

    }

    export function getAllInitValueItemSetting(settingId, categoryCd, baseDate) {
        return ajax(format(regpath + paths.getAllInitValueItemSetting, settingId, categoryCd, baseDate));
    }

    export function getSelfRoleAuth() {
        return ajax(regpath + paths.getSelfRoleAuth);
    }

    export function getLayoutByCreateType(command) {
        return ajax(regpath + paths.getLayoutByCreateType, command);
    }

    export function addNewEmployee(command) {
        return ajax(regpath + paths.addNewEmployee, command);
    }

}

