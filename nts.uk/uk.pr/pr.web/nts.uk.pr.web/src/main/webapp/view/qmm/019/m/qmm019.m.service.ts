module nts.uk.pr.view.qmm019.m {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getFormulaByYearMonth: "core/wageprovision/statementlayout/getFormulaByYearMonth/{0}"
        }

        export function getFormulaByYearMonth(yearMonth: number): JQueryPromise<any> {
            let _path = format(paths.getFormulaByYearMonth, yearMonth);
            return ajax('pr', _path);
        }
    }
}