module nts.uk.pr.view.qmm003.d.service {
    import ajax = nts.uk.request.ajax;

    let paths = {
        checkBeforeDelete: "cts/pr/transfer/rsdttaxpayee/check-before-delete",
        removeList: "cts/pr/transfer/rsdttaxpayee/delete-list",
        getAllResidentTaxPayee: "cts/pr/transfer/rsdttaxpayee/get-all-resident-tax-payee"
    };

    export function getAllResidentTaxPayee(): JQueryPromise<any> {
        return ajax('pr', paths.getAllResidentTaxPayee);
    }
    
    export function checkBeforeDelete(codes: Array<string>) {
        return ajax('pr', paths.checkBeforeDelete, codes);
    }
    
    export function removeList(codes: Array<string>) {
        return ajax('pr', paths.removeList, codes);
    }
    
}