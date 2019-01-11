module nts.uk.pr.view.qmm003.c.service {
    
    import ajax = nts.uk.request.ajax;

    let paths = {
        getAllResidentTaxPayee: "ctx/pr/transfer/rsdttaxpayee/get-all-resident-tax-payee"
    };

    export function getAllResidentTaxPayee(): JQueryPromise<any> {
        return ajax('pr', paths.getAllResidentTaxPayee);
    }

}