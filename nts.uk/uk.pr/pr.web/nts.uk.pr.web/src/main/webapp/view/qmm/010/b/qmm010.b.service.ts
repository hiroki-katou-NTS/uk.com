module nts.uk.pr.view.qmm010.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findAllSocialOffice: "ctx/pr/core/socialinsurance/socialinsuranceoffice/findAll"
    }
    /**
     * get all
    */
    export function findAllSocialOffice(): JQueryPromise<any> {
        return ajax(paths.findAllSocialOffice);
    }
    

}