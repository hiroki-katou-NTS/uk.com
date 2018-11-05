module nts.uk.pr.view.qmm019.d {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getStatementItem: "core/wageprovision/statementlayout/getStatementItem",
            getPaymentItemStById: "core/wageprovision/statementlayout/getPaymentItemStById/{0}/{1}",
            getBreakdownItemStById: "ctx/pr/core/breakdownItem/{0}/{1}"
        }

        export function getStatementItem(): JQueryPromise<any> {
            return ajax('pr', paths.getStatementItem);
        }

        export function getPaymentItemStById(categoryAtr: number, itemNameCode: string): JQueryPromise<any> {
            let _path = format(paths.getPaymentItemStById, categoryAtr, itemNameCode);
            return ajax('pr', _path);
        }

        export function getBreakdownItemStById(categoryAtr: number, itemNameCode: string): JQueryPromise<any> {
            let _path = format(paths.getBreakdownItemStById, categoryAtr, itemNameCode);
            return ajax('pr', _path);
        }
    }
}