module nts.uk.pr.view.qmm019.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getStatementLayoutAndLastHist: "core/wageprovision/statementlayout/getStatementLayoutAndLastHist/{0}",
            getHistByCidAndCodeAndAfterDate: "core/wageprovision/statementlayout/getHistByCidAndCodeAndAfterDate/{0}/{1}"
        }

        export function getStatementLayoutAndLastHist(code: string): JQueryPromise<any> {
            let _path = format(paths.getStatementLayoutAndLastHist, code);
            return ajax('pr', _path);
        }

        export function getHistByCidAndCodeAndAfterDate(code: string, startMonth: number): JQueryPromise<any> {
            let _path = format(paths.getHistByCidAndCodeAndAfterDate, code, startMonth);
            return ajax('pr', _path);
        }
    }
}