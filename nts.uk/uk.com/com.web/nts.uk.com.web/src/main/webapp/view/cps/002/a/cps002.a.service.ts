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
        'checkDuplicateEmpCodeOrCardNo': 'basic/organization/employee/checkDuplicateEmpCodeOrCardNo/{0}/{1}',

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

    export function checkDuplicateEmpCodeOrCardNo(employeeCode, cardNo) {
        return ajax(format(paths.isDuplicateEmpCodeOrCardNo, employeeCode, cardNo));
    }

}

