module nts.uk.pr.view.qmm017.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        editFormulaHistory: "ctx/pr/core/wageprovision/formula/editFormulaHistory",
        deleteFormulaHistory: "ctx/pr/core/wageprovision/formula/deleteFormulaHistory"
    }

    export function editFormulaHistory(command): JQueryPromise<any> {
        return ajax(paths.editFormulaHistory, command);

    }

    export function deleteFormulaHistory(command): JQueryPromise<any> {
        return ajax(paths.deleteFormulaHistory, command);
    }

}