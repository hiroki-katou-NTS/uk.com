module cps001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths : any = {
        'getDetails': 'basic/organization/employee/getemployeetodelete/{0}',
        'deleteEmp': 'basic/organization/employee/deleteemployee',
    };


   
    export function getEmployee(employeeId : string) {
        return ajax(format(paths.getDetails, employeeId));
    }
    
    export function deleteEmp(modelDelete : any) {
        return ajax(paths.deleteEmp, modelDelete);
    }
}