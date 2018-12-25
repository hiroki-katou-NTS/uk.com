module nts.uk.pr.view.qmm038.a.service {
    import ajax = nts.uk.request.ajax;
    let paths = {
        getStatementItemName: "ctx/pr/shared/employeeaverwage/start",
        updateStatementItemName: "ctx/pr/shared/employeeaverwage/update",
        findByEmployeeId: "ctx/pr/shared/employeeaverwage/findemployee",
    };

    export function defaultData(): JQueryPromise<any> {
        return ajax("pr", paths.getStatementItemName);
    }

    export function update(command): JQueryPromise<any> {
        return ajax('pr', paths.updateStatementItemName, command);
    }

    export function findByEmployee(command): JQueryPromise<any> {
        return ajax("pr", paths.findByEmployeeId, command);
    }
}