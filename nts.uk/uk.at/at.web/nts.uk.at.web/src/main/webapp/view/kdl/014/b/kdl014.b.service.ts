module kdl014.b.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode/{0}/{1}",
        getStampNumberByListPersonId: "at/record/stamp/stampcard/getliststampnumberbylstempcode",
        getPersonIdByEmployeeCode: "basic/organization/employee/getPersonIdByEmployeeCode/{0}",
        getListPersonByListEmployee: "basic/organization/employee/getListPersonIdByEmployeeCode"
    }
    /**
     * get list List Stamp by Code
     */
    export function getStampByCode(arrCardNumber: Array<string>, startDate, endDate): JQueryPromise<any> {
        arrCardNumber = arrCardNumber ? arrCardNumber: [];
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getStampByEmployeeCode, startDate, endDate), arrCardNumber);
    }

    /**
     * get list PersonId 
     */
    export function getPersonIdByEmployee(employeeCode): JQueryPromise<any> {
        employeeCode = employeeCode ? employeeCode : null;
        return nts.uk.request.ajax("com", nts.uk.text.format(paths.getPersonIdByEmployeeCode,employeeCode));
    }

    /**
     * get list Stamp Number by List PersonID 
     */
    export function getStampNumberByListPersonId(arrPersonId: Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampNumberByListPersonId, arrPersonId);
    }

    /**
     * get list Person ID by Employee CD 
     */
    export function getListPersonByListEmployee(arrEmployeeCD: Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getListPersonByListEmployee, arrEmployeeCD);
    }

}