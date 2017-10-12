module cps001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getDetails: "basic/organization/employee/getemployeeinfo/{0}"
    };


   /**
    * add  Maintenance Layout
    */
   
    export function getEmployee(employeeId) {
        return ajax(format(paths.getDetails, employeeId));
    }
}