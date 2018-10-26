module nts.uk.pr.view.qmm016.j.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        editWageTableHistory: "ctx/core/socialinsurance/healthinsurance/editHistory",
        deleteWageTableHistory: "ctx/core/socialinsurance/healthinsurance/deleteHistory"
    }
    /**
     * get all
     */
    export function editWageTableHistory(command): JQueryPromise<any> {
        return ajax(paths.editWageTableHistory, command);

    }

    export function deleteWageTableHistory(command): JQueryPromise<any> {
        return ajax(paths.deleteWageTableHistory, command);
    }

}