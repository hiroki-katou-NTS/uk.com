module nts.uk.pr.view.qmm041.c.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
        updateHistory: "ctx/pr/core/wageprovision/empsalunitprice/updateHistory",
        deleteHistory: "ctx/pr/core/wageprovision/empsalunitprice/deleteHistory"
    }

    export function editEmpSalUnitPriceHis(command): JQueryPromise<any> {
        return ajax(paths.updateHistory, command);
    }

    export function deleteEmpSalUnitPriceHis(command): JQueryPromise<any> {
        return ajax(paths.deleteHistory, command);
    }
}