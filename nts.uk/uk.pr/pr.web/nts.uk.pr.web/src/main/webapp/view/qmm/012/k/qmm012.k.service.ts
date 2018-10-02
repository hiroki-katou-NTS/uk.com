module nts.uk.pr.view.qmm012.k.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllTaxAmountByCompanyId: "ctx/pr/core/taxamount/getAllTaxAmountByCompanyId"
    }

    export function getAllTaxAmountByCompanyId(): JQueryPromise<any> {
        return ajax("pr", paths.getAllTaxAmountByCompanyId);
    };
}
