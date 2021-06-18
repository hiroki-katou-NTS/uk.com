module knr002.h.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getEmployees: "screen/at/employeestransfer/getEmployees",
        registSpecifiedEmps: "screen/at/employeestransfer/registSpecifiedEmps",
    };
    /**
     * GetEmployeesScreenQueryWS
     */
    export function getEmployees(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getEmployees+ "/" + empInfoTerCode);
    }
    /**
     * RegistSpecifiedEmployeeOnScreen
     */
    export function registSpecifiedEmps(registDto: any): JQueryPromise<any> {
        return ajax(paths.registSpecifiedEmps, registDto);
    }
}

  