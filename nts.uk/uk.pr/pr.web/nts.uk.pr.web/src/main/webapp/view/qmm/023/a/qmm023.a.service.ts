module nts.uk.pr.view.qmm023.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import exportFile = nts.uk.request.exportFile;

    var paths = {
        getAllTaxAmountByCompanyId: "ctx/pr/core/taxamount/getAllTaxAmountByCompanyId",
        addTaxExemptLimit: "ctx/pr/core/taxamount/addTaxExemptLimit",
        updateTaxExemptLimit: "ctx/pr/core/taxamount/updateTaxExemptLimit",
        removeTaxExemptLimit: "ctx/pr/core/taxamount/removeTaxExemptLimit",
        exportExcel: "file/core/wageprovision/taxexemptionlimit/exportExcel"
    }

    export function getAllTaxAmountByCompanyId(): JQueryPromise<any> {
        return ajax("pr", paths.getAllTaxAmountByCompanyId);
    };
    export function addTaxExemptLimit(TaxExemptLimit: any): JQueryPromise<any> {
        return ajax('pr', paths.addTaxExemptLimit, TaxExemptLimit);
    };
    export function updateTaxExemptLimit(TaxExemptLimit: any): JQueryPromise<any> {
        return ajax('pr', paths.updateTaxExemptLimit, TaxExemptLimit);
    };
    export function removeTaxExemptLimit(TaxExemptLimit: any): JQueryPromise<any> {
        return ajax('pr', paths.removeTaxExemptLimit, TaxExemptLimit);
    };
    export function exportExcel(): JQueryPromise<any> {
        let _path = format(paths.exportExcel);
        return exportFile(_path);
    }
}
