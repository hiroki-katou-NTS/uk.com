module nts.uk.pr.view.qmm019.o {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {

        var paths = {
            getAllStatementItemData: "ctx/pr/core/wageprovision/statementitem/getAllStatementItemData/{0}/{1}"
        }

        export function getAllStatementItemData(categoryAtr: number, isIncludeDeprecated: boolean): JQueryPromise<any> {
            let _path = format(paths.getAllStatementItemData, categoryAtr, isIncludeDeprecated);
            return ajax('pr', _path);
        }
    }
}