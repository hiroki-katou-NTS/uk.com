module nts.uk.pr.view.qmm003.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAllResidentTaxPayee: "ctx/pr/transfer/rsdttaxpayee/get-all-resident-tax-payee"
    };

    export function getAllResidentTaxPayee(): JQueryPromise<any> {
        return ajax('pr', paths.getAllResidentTaxPayee);
    }
    
}