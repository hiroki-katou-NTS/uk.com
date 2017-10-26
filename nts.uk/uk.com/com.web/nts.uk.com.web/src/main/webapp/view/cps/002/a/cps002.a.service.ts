module cps002.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getData': 'ctx/bs/person/newlayout/get',
        'getUserSetting': 'ctx/bs/person/info/setting/user/getUserSetting',
        'getLastRegHistory': 'ctx/bs/person/info/setting/regisHistory/getLastRegHistory',
        'getEmployeeCode': 'basic/organization/employee/getGenerateEmplCode',
        'getCardNumber': 'basic/organization/employee/getGenerateCardNo',
        'getGenerateEmplCodeAndComId': 'basic/organization/employee/getGenerateEmplCodeAndComId',
        'validateEmpInfo': 'basic/organization/employee/validateEmpInfo',
        'getCopySetting': 'ctx/bs/person/info/setting/copySetting/getCopySetting',
        'getAllInitValueSetting': 'ctx/bs/person/info/setting/init/findAllHasChild',
        'getAllInitValueCtgSetting': 'ctx/bs/person/info/setting/init/ctg/findAllBySetId/{0}',
        'getAllInitValueItemSetting': 'regpersoninfo/init/item/findInit/{0}/{1}',
        'getSelfRoleAuth': 'ctx/bs/person/roles/auth/getSelfAuth'

    };

    export function getLayout() {
        return ajax(paths.getData);
    }

    export function getUserSetting() {
        return ajax(paths.getUserSetting);
    }

    export function getLastRegHistory() {
        return ajax(paths.getLastRegHistory);
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

    export function validateEmpInfo(employeeCode, cardNo) {
        return ajax(paths.validateEmpInfo, { employeeCode: employeeCode, cardNo: cardNo });
    }

    export function getCopySetting() {
        return ajax(paths.getCopySetting);
    }

    export function getAllInitValueSetting() {
        return ajax(paths.getAllInitValueSetting);
    }

    export function getAllInitValueCtgSetting(settingId: string) {
        return ajax(format(paths.getAllInitValueCtgSetting, settingId));

    }

    export function getAllInitValueItemSetting(settingId, categoryCd) {
        return ajax(format(paths.getAllInitValueItemSetting, settingId, categoryCd));
    }

    export function getSelfRoleAuth() {
        return ajax(paths.getSelfRoleAuth);
    }

}

