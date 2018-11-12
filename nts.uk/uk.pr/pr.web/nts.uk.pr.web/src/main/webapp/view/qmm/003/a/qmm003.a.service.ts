module nts.uk.pr.view.qmm003.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAllResidentTaxPayee: "cts/pr/transfer/rsdttaxpayee/get-all-resident-tax-payee",
        getResidentTaxPayee :"cts/pr/transfer/rsdttaxpayee/get-resident-tax-payee/{0}",
        register: "cts/pr/transfer/rsdttaxpayee/reg-resident-tax-payee",
        checkBeforeDelete: "cts/pr/transfer/rsdttaxpayee/check-before-delete",
        remove: "cts/pr/transfer/rsdttaxpayee/delete-resident-tax-payee/{0}"
    };

    export function getAllResidentTaxPayee(): JQueryPromise<any> {
        return ajax('pr', paths.getAllResidentTaxPayee);
    }
    
    export function getResidentTaxPayee(code: string) {
        return ajax('pr', format(paths.getResidentTaxPayee, code));
    }

    export function register(data: any) {
        return ajax('pr', paths.register, data);
    }

    export function checkBeforeDelete(code: string) {
        return ajax('pr', paths.checkBeforeDelete, [code]);
    }
    
    export function remove(code: string) {
        return ajax('pr', format(paths.remove, code));
    }

}