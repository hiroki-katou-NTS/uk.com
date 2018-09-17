module nts.uk.pr.view.qmm008.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findAllOffice: "ctx/core/socialinsurance/healthinsurance/getByCompanyId"    
    }
    /**
     * get all
    */
    export function findAllOffice(): JQueryPromise<any> {
        return ajax(paths.findAllOffice);
    }
}