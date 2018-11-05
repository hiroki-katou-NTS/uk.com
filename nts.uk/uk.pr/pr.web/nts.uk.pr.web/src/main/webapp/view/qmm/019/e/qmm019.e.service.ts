module nts.uk.pr.view.qmm019.e {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getStatementItem: "core/wageprovision/statementlayout/getStatementItem",
            getPaymentItemStById: "core/wageprovision/statementlayout/getPaymentItemStById/{0}/{1}",
            getDeductionItemStById: "core/wageprovision/statementlayout/getDeductionItemStById/{0}/{1}",
            getAllBreakdownItemSetById: "ctx/pr/core/breakdownItem/getAllBreakdownItemSetById/{0}/{1}"
        }

        export function getStatementItem(): JQueryPromise<any> {
            return ajax('pr', paths.getStatementItem);
        }

        export function getPaymentItemStById(categoryAtr: number, itemNameCode: string): JQueryPromise<any> {
            let _path = format(paths.getPaymentItemStById, categoryAtr, itemNameCode);
            return ajax('pr', _path);
        }

        export function getDeductionItemStById(categoryAtr: number, itemNameCode: string): JQueryPromise<any> {
            let _path = format(paths.getDeductionItemStById, categoryAtr, itemNameCode);
            return ajax('pr', _path);
        }

        export function getAllBreakdownItemSetById(categoryAtr: number, itemNameCode: string): JQueryPromise<any> {
            let _path = format(paths.getAllBreakdownItemSetById, categoryAtr, itemNameCode);
            return ajax('pr', _path);
        }
    }
}