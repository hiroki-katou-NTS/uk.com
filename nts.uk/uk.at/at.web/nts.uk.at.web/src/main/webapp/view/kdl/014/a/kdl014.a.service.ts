module kdl014.a.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode",
        getStampNumberByPersonId: "at/record/stamp/stampcard/getstampnumberbyempcode",]
        getPersonIdByEmployeeCode:""
    }
    /**
     * get list List Stamp by Code
     */
    export function getStampByCode(cardNumber, startDate, endDate): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampByEmployeeCode + "/" + cardNumber + "/" + startDate + "/" + endDate);
    }
    
    /**
     * get list Stamp Number
     */
    export function getStampNumberByPersonId(personId): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampNumberByPersonId + "/" + personId);
    }
}