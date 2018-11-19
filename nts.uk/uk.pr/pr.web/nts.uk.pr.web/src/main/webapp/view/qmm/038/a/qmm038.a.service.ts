module nts.uk.pr.view.qmm038.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getStatementItemName: "pr/core/employeeaverwage/start",
        updateStatementItemName: "pr/core/employeeaverwage/update",
        findByEmployeeId: "pr/core/employeeaverwage/findemployee",
    }
    export function defaultData(): JQueryPromise<any> {
       return ajax("pr",paths.getStatementItemName);
    }
    export function update(command) : JQueryPromise<any>{
        return ajax('pr', paths.updateStatementItemName, command);
    }
    export function findByEmployee(command): JQueryPromise<any> {
        return ajax("pr",paths.findByEmployeeId,command);
    }
}