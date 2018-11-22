module nts.uk.pr.view.qmm019.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        let paths = {
            getAllStatementLayoutAndHist: "core/wageprovision/statementlayout/getAllStatementLayoutAndHist",
            getStatementLayoutHistData: "core/wageprovision/statementlayout/getStatementLayoutHistData/{0}/{1}"
        };

        export function getAllStatementLayoutAndHist() : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.getAllStatementLayoutAndHist);
        }

        export function getStatementLayoutHistData(code: string, histId: string): JQueryPromise<any> {
            let _path = format(paths.getStatementLayoutHistData, code, histId);
            return ajax('pr', _path);
        }
    }
}