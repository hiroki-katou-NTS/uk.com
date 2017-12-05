module cps002.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let
        regpath = "ctx/pereg/",
        basicpath = "ctx/pereg/person/"
        , otherpath: any = {
            getEmployeeCode: 'basic/organization/employee/getGenerateEmplCode',
            getCardNumber: 'basic/organization/employee/getGenerateCardNo'
        }, basicpaths: any = {
            getLayout: 'newlayout/get',
            getAllInitValueSetting: 'info/setting/init/findAllHasChild',
            getSelfRoleAuth: 'roles/auth/getSelfAuth',
        },
        regpaths: any = {
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
        }
        ;

    export function getLayout() {
        return ajax(basicpath + basicpaths.getLayout);
    }

    export function getUserSetting() {
        return ajax(regpath + regpaths.getUserSetting);
    }

    export function getLastRegHistory() {
        return ajax(regpath + regpaths.getLastRegHistory);
    }

    export function getEmployeeCode(employeeLetter) {
        return ajax(otherpath.getEmployeeCode, employeeLetter);
    }

    export function getCardNumber(cardLetter) {
        return ajax(otherpath.getCardNumber, cardLetter);
    }

    export function getEmployeeCodeAndComId(employeeLetter) {
        return ajax(otherpath.getCardNumber, employeeLetter);
    }

    export function validateEmpInfo(command) {
        return ajax(regpath + regpaths.validateEmpInfo, command);
    }

    export function getCopySetting() {
        return ajax(regpath + regpaths.getCopySetting);
    }

    export function getAllCopySettingItem(employeeId, categoryCd, baseDate) {
        return ajax(format(regpath + regpaths.getAllCopySettingItem, employeeId, categoryCd, baseDate));
    }

    export function getAllInitValueSetting() {
        return ajax(basicpath + basicpaths.getAllInitValueSetting);
    }

    export function getAllInitValueCtgSetting(settingId: string) {
        return ajax(format(regpath + regpaths.getAllInitValueCtgSetting, settingId));

    }

    export function getAllInitValueItemSetting(settingId, categoryCd, baseDate) {
        return ajax(format(regpath + regpaths.getAllInitValueItemSetting, settingId, categoryCd, baseDate));
    }

    export function getSelfRoleAuth() {
        return ajax(basicpath + basicpaths.getSelfRoleAuth);
    }

    export function getLayoutByCreateType(command) {
        return ajax(regpath + regpaths.getLayoutByCreateType, command);
    }

    export function addNewEmployee(command) {
        return ajax(regpath + regpaths.addNewEmployee, command);
    }

}

