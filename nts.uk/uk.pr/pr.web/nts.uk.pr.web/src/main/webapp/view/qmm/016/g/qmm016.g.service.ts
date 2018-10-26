module nts.uk.pr.view.qmm016.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

        let paths = {
            getAllStatementItemData: "ctx/pr/core/wageprovision/statementitem/getAllStatementItemData/{0}/{1}"
        }

        export function getAllStatementItemData(categoryAtr: number, isdisplayAbolition: boolean): JQueryPromise<any> {
            let _path = format(paths.getAllStatementItemData, categoryAtr, isdisplayAbolition);
            return ajax('pr', _path);
        }
}
