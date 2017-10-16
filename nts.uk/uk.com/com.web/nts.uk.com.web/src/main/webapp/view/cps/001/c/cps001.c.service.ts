module cps001.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getListTemporaryEmpDelete': 'basic/organization/employee/getallemployeetodelete',
        'getDetail': 'basic/organization/employee/getdetailemployeetodelete/{0}',
        'restoreData': 'basic/organization/employee/restoredata/'
    };

    export function getData() {
        return ajax(paths.getListTemporaryEmpDelete);
    }

    export function getDetail(employeeId: any) {
        return ajax(format(paths.getDetail, employeeId));
    }

    export function restoreData(command) {
        return ajax(paths.restoreData, command);
    }
}