module kdl014.b.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode",
        getStampNumberByPersonId: "at/record/stamp/stampcard/getstampnumberbyempcode",
        getStampNumberByListPersonId: "at/record/stamp/stampcard/getliststampnumberbylstempcode",
        getPersonIdByEmployeeCode: "basic/organization/getPersonIdByEmployeeCode"
    }
    /**
     * get list List Stamp by Code
     */
    export function getStampByCode(arrCardNumber : Array<string>, startDate, endDate): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampByEmployeeCode + "/" + startDate + "/" + endDate, arrCardNumber);
    }
 
    /**
     * get list Stamp Number by PersonID
     */
    export function getStampNumberByPersonId(personId): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampNumberByPersonId + "/" + personId);
 
    }
    /**
     * get list PersonId 
     */
    export function getPersonIdByEmployee(employeeCode): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getPersonIdByEmployeeCode + "/" + employeeCode);
    }
    
    /**
     * get list Stamp Number by List PersonID 
     */
    export function getStampNumberByListPersonId(arrPersonId: Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampNumberByListPersonId,arrPersonId);
    }

}