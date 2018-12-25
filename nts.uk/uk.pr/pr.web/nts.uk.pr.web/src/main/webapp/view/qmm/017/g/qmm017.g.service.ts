module nts.uk.pr.view.qmm017.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        calculation: "ctx/pr/core/wageprovision/formula/calculation",
        getProcessYearMonthAndReferenceTime: "ctx/pr/core/wageprovision/formula/getProcessYearMonthAndReferenceTime"
    }
    export function calculation(formula): JQueryPromise<any> {
        return ajax(paths.calculation, formula);
    }
    export function getProcessYearMonthAndReferenceTime(): JQueryPromise<any> {
        return ajax(paths.getProcessYearMonthAndReferenceTime);
    }
}
