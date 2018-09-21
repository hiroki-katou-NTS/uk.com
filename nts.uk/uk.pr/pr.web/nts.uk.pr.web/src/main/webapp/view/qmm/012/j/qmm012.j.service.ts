module nts.uk.pr.view.qmm012.j.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getStatementItemAndStatementItemName: "ctx/pr/core/wageprovision/statementitem/getStatementItemAndStatementItemName/{0}",
        updateStatementItemName: "ctx/pr/core/wageprovision/statementitem/updateStatementItemName",
    }

    export function getStatementItemAndStatementItemName(categoryAtr: number): JQueryPromise<any> {
        var _path = format(paths.getStatementItemAndStatementItemName, categoryAtr);
        return ajax('pr', _path);
    };
    
    export function updateStatementItemName(statementItemName: Array<any>): JQueryPromise<any> {
        return ajax('pr', paths.updateStatementItemName, statementItemName);
    };
}
