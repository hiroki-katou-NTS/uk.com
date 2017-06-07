module kdl014.a.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode",
        getPersonIdByEmpCode: "basic/organization/findEmployeeByEmployeeCode"
    }
    /**
     * get list Stamp by Employee Code end period
     */
    export function getStampByCode(cardNumber, startDate, endDate): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampByEmployeeCode + "/" + cardNumber + "/" + startDate + "/" + endDate);
    }
    /**
     * get item employee by code 
     */
    export function getPersonByEmpCode(employeeCode): JQueryPromise<any> {
        return nts.uk.request.ajax("basic", paths.getPersonIdByEmpCode + "/" + employeeCode); 
    }
}