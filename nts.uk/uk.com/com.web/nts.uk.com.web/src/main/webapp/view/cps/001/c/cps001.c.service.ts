module cps001.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getListEmployeeDataMngInfo': 'basic/organization/deleteempmanagement/getallemployeetodelete',
        'getDetail': 'basic/organization/deleteempmanagement/getdetailemployeetodelete/{0}',
        'restoreData': 'basic/organization/deleteempmanagement/restoredata',
        'deleteEmp': 'basic/organization/deleteempmanagement/deleteemp/{0}'
    };

    export function getData() {
        return ajax(paths.getListEmployeeDataMngInfo);
    }

    export function getDetail(employeeId: any) {
        return ajax(format(paths.getDetail, employeeId));
    }

    export function restoreData(command : any) {
        return ajax(paths.restoreData, command);
    }
    
    export function removedata(employeeId: any) {
        return ajax(format(paths.deleteEmp, employeeId));
    }
}