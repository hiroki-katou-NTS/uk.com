module nts.uk.pr.view.qmm012.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllBreakdownItemSetById: "ctx/pr/core/breakdownItem/getAllBreakdownItemSetById"
    }

    export function getAllBreakdownItemSetById(): JQueryPromise<any> {
        return ajax("pr", paths.getAllBreakdownItemSetById);
    };
}
