module nts.uk.pr.view.qmm036.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        updateBreakdownAmountHis: "ctx/pr/core/breakdownAmountHis/updateBreakdownAmountHis",
        removeBreakdownItemSet: "ctx/pr/core/breakdownAmountHis/removeBreakdownAmountHis"
    }

    export function updateBreakdownAmountHis(command): JQueryPromise<any> {
        return ajax(paths.updateBreakdownAmountHis, command);
    }

    export function removeBreakdownItemSet(command): JQueryPromise<any> {
        return ajax(paths.removeBreakdownItemSet, command);
    }
}