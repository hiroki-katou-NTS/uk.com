module cps001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths : any = {
        'getDetails': 'basic/organization/deleteempmanagement/getemployeetodelete/{0}',
        'deleteEmp': 'basic/organization/deleteempmanagement/deleteemployee',
    };


   
    export function getEmployee(employeeId : string) {
        return ajax(format(paths.getDetails, employeeId));
    }
    
    export function deleteEmp(modelDelete : any) {
        return ajax(paths.deleteEmp, modelDelete);
    }
}