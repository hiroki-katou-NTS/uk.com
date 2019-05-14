module nts.uk.pr.view.qmm003.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import exportFile = nts.uk.request.exportFile;

    let paths = {
        getAllResidentTaxPayee: "ctx/pr/transfer/rsdttaxpayee/get-all-resident-tax-payee",
        getResidentTaxPayee :"ctx/pr/transfer/rsdttaxpayee/get-resident-tax-payee/{0}",
        getResidentTaxPayeeZero :"ctx/pr/transfer/rsdttaxpayee/get-resident-tax-payee-company-zero/{0}",
        register: "ctx/pr/transfer/rsdttaxpayee/reg-resident-tax-payee",
        checkBeforeDelete: "ctx/pr/transfer/rsdttaxpayee/check-before-delete",
        remove: "ctx/pr/transfer/rsdttaxpayee/delete-resident-tax-payee/{0}",
        exportFilePdf : "file/core/laborInsurance/residentTaxPayeeExport/export"
    };

    export function getAllResidentTaxPayee(): JQueryPromise<any> {
        return ajax('pr', paths.getAllResidentTaxPayee);
    }
    
    export function getResidentTaxPayee(code: string) {
        return ajax('pr', format(paths.getResidentTaxPayee, code));
    }
    
    export function getResidentTaxPayeeZero(code: string) {
        return ajax('pr', format(paths.getResidentTaxPayeeZero, code));
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

    export function exportFilePdf(): JQueryPromise<any> {
        let _path = format(paths.exportFilePdf);
        return exportFile(_path);
    }
}