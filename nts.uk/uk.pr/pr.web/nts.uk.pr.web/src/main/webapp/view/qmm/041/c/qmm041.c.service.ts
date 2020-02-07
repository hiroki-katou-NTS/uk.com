module nts.uk.pr.view.qmm041.c.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
        updateHistory: "ctx/pr/core/wageprovision/empsalunitprice/updateHistory",
        deleteHistory: "ctx/pr/core/wageprovision/empsalunitprice/deleteHistory"
    }

    export function updateHistory(command): JQueryPromise<any> {
        return ajax("pr", paths.updateHistory, command);
    }

    export function deleteHistory(command): JQueryPromise<any> {
        return ajax("pr", paths.deleteHistory, command);
    }
}