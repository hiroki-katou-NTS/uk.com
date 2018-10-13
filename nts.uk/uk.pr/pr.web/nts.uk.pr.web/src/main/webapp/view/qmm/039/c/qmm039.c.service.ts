module nts.uk.pr.view.qmm039.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        editSalIndividualAmountHistory: "ctx/pr/core/ws/wageprovision/individualwagecontract/editHistory"
    }

    export function editSalIndividualAmountHistory(command): JQueryPromise<any> {
        return ajax(paths.editSalIndividualAmountHistory, command);
    }
}