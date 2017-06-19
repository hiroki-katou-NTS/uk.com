module kdl014.a.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode",
        getStampNumberByPersonId: "at/record/stamp/stampcard/getstampnumberbyempcode",
        getPersonIdByEmployeeCode: "basic/organization/employee/getPersonIdByEmployeeCode"
    }
    /**
     * get list List Stamp by Code
     */
    export function getStampByCode(arrCardNumber : Array<string>, startDate, endDate): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampByEmployeeCode + "/" + startDate + "/" + endDate, arrCardNumber);
    }
 
    /**
     * get list Stamp Number
     */
    export function getStampNumberByPersonId(personId): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampNumberByPersonId + "/" + personId);
 
    }
    export function getPersonIdByEmployee(employeeCode): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getPersonIdByEmployeeCode + "/" + employeeCode);
    }

}