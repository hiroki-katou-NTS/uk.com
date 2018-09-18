module nts.uk.pr.view.qmm008.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findAllOffice: "ctx/pr/core/socialinsurance/healthinsurance/getByCompanyId",
        findEmployeeHealthInsuranceByHistoryId: "ctx/pr/core/socialinsurance/healthinsurance/getByHistoryId/{0}" ,
        addEmployeeHealthInsurance: "ctx/pr/core/socialinsurance/healthinsurance/add"
    }
    /**
     * get all
    */
    export function findAllOffice(): JQueryPromise<any> {
        return ajax(paths.findAllOffice);
    }
    
    export function findEmployeeHealthInsuranceByHistoryId (historyId: string): JQueryPromise<any> {
        return ajax(format(paths.findEmployeeHealthInsuranceByHistoryId, historyId));
    }
    
    export function addEmployeeHealthInsurance(command): JQueryPromise<any> {
        return ajax(paths.addEmployeeHealthInsurance, command);
    }
}