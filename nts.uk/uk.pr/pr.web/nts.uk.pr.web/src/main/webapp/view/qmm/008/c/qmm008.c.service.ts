module nts.uk.pr.view.qmm008.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findAllOffice: "ctx/core/socialinsurance/welfarepensioninsurance/getAll",
        findEmployeePensionByHistoryId: "ctx/core/socialinsurance/welfarepensioninsurance/getByHistoryId/{0}",
        registerEmployeePension: "ctx/core/socialinsurance/welfarepensioninsurance/registerEmployeePension",
        checkWelfarePensionInsuranceGradeFeeChange: "ctx/core/socialinsurance/welfarepensioninsurance/checkGradeFeeChange"
    }
    /**
     * get all
    */
    export function findAllOffice(): JQueryPromise<any> {
        return ajax(paths.findAllOffice);
    }
    
    export function findEmployeePensionByHistoryId (historyId: string): JQueryPromise<any> {
        return ajax(format(paths.findEmployeePensionByHistoryId, historyId));
    }
    
    export function registerEmployeePension(command): JQueryPromise<any> {
        return ajax(paths.registerEmployeePension, command);
    }
        
    export function checkWelfarePensionInsuranceGradeFeeChange(command): JQueryPromise<any> {
        return ajax(paths.checkWelfarePensionInsuranceGradeFeeChange, command);
    }
    
}