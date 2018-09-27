module nts.uk.pr.view.qmm008.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findAllOffice: "ctx/core/socialinsurance/healthinsurance/getByCompanyId",
        findEmployeeHealthInsuranceByHistoryId: "ctx/core/socialinsurance/healthinsurance/getByHistoryId/{0}" ,
        registerEmployeeHealthInsurance: "ctx/core/socialinsurance/healthinsurance/register",
        checkHealthInsuranceGradeFeeChange: "ctx/core/socialinsurance/healthinsurance/checkGradeFeeChange"
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
    
    export function registerEmployeeHealthInsurance(command): JQueryPromise<any> {
        return ajax(paths.registerEmployeeHealthInsurance, command);
    }
    
    export function checkHealthInsuranceGradeFeeChange(command): JQueryPromise<any> {
        return ajax(paths.checkHealthInsuranceGradeFeeChange, command);
    }
}