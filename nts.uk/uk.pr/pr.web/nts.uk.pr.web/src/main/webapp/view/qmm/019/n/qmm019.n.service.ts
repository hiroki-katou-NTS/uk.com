module nts.uk.pr.view.qmm019.n {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getWageTableByYearMonth: "core/wageprovision/statementlayout/getWageTableByYearMonth/{0}"
        }

        export function getWageTableByYearMonth(yearMonth: number): JQueryPromise<any> {
            let _path = format(paths.getWageTableByYearMonth, yearMonth);
            return ajax('pr', _path);
        }
    }
}