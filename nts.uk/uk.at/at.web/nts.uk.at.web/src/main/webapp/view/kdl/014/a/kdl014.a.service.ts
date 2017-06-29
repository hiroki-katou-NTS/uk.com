module kdl014.a.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode/{0}/{1}",
        getStampNumberByPersonId: "at/record/stamp/stampcard/getstampnumberbyempcode/{0}",
        getPersonIdByEmployeeCode: "basic/organization/employee/getPersonIdByEmployeeCode/{0}"
    }
    /**
     * get list List Stamp by Code
     */
    export function getStampByCode(arrCardNumber: Array<string>, startDate, endDate): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getStampByEmployeeCode, startDate, endDate), arrCardNumber);
    }

    /**
     * get list Stamp Number
     */
    export function getStampNumberByPersonId(personId): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getStampNumberByPersonId, personId));
    }
    export function getPersonIdByEmployee(employeeCode): JQueryPromise<any> {
        employeeCode = employeeCode ? employeeCode : null;
        return nts.uk.request.ajax("com", nts.uk.text.format(paths.getPersonIdByEmployeeCode, employeeCode));
    }

}