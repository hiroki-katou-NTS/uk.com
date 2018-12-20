module nts.uk.pr.view.qmm016.j.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        editWageTableHistory: "ctx/pr/core/wageprovision/wagetable/editHistory",
        deleteWageTableHistory: "ctx/pr/core/wageprovision/wagetable/deleteHistory"
    }
    
    export function editWageTableHistory(command): JQueryPromise<any> {
        return ajax(paths.editWageTableHistory, command);
    }

    export function deleteWageTableHistory(command): JQueryPromise<any> {
        return ajax(paths.deleteWageTableHistory, command);
    }

}