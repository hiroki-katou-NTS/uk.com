module cps001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getEmployeeInfo': 'basic/organization/deleteempmanagement/getemployeetodelete/{0}',
        'getPersonInfo': 'bs/employee/person/findBypId/{0}',
        'deleteEmp': 'basic/organization/deleteempmanagement/deleteemployee',
    };



    export function getEmployeeInfo(employeeId: string) {
        return ajax(format(paths.getEmployeeInfo, employeeId));
    }
    
     export function getPersonInfo(personId: string) {
        return ajax(format(paths.getPersonInfo, personId));
    }

    export function deleteEmp(modelDelete: any) {
        return ajax(paths.deleteEmp, modelDelete);
    }
}