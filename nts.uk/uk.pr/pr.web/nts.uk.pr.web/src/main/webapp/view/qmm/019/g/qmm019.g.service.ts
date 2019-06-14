module nts.uk.pr.view.qmm019.g {
    import ajax = nts.uk.request.ajax;

    export module service {
        var paths = {
            getStatementItem: "core/wageprovision/statementlayout/getStatementItem"
        }

        export function getStatementItem(dataDto: any): JQueryPromise<any> {
            return ajax('pr', paths.getStatementItem, dataDto);
        }
    }
}