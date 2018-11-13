module nts.uk.pr.view.qmm038.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getStatementItemName: "pr/core/employeeaverwage/start",
        updateStatementItemName: "pr/core/fromsetting/update"
    }
    export function defaultData(): JQueryPromise<any> {
       return ajax("pr",paths.getStatementItemName);
    }
    export function update(statementItemName: Array<any>) : JQueryPromise<any>{
        return ajax('pr', paths.updateStatementItemName, statementItemName);
    }
}