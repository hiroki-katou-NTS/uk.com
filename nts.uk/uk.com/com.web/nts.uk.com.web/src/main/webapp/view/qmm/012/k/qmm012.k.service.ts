module nts.uk.com.view.qmm012.k.service {
    // import model = cmf002.share.model;
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllTaxAmountByCompanyId: "exio/qmm/taxamount/getAllTaxAmountByCompanyId"
    }

    export function getAllTaxAmountByCompanyId(): JQueryPromise<any> {
        return ajax("com", paths.getAllTaxAmountByCompanyId);
    };
}
