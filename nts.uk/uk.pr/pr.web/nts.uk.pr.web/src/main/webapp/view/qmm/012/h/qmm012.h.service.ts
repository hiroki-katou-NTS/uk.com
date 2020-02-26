module nts.uk.pr.view.qmm012.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getValidityPeriodAndCycleSet: "ctx/pr/core/wageprovision/statementitem/validityperiodset/getValidityPeriodAndCycleSet/{0}/{1}",
        registerValidityPeriodAndCycleSet: "ctx/pr/core/wageprovision/statementitem/validityperiodset/registerValidityPeriodAndCycleSet",
    }

    export function getValidityPeriodAndCycleSet(categoryAtr: string, itemNameCd: string): JQueryPromise<any> {
        return ajax(format(paths.getValidityPeriodAndCycleSet, categoryAtr, itemNameCd));
    }

    export function registerValidityPeriodAndCycleSet(command): JQueryPromise<any> {
        return ajax(paths.registerValidityPeriodAndCycleSet, command);
    }
}