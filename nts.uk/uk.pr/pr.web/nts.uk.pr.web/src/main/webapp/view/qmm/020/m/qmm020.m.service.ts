module nts.uk.pr.view.qmm020.m.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths : any = {
        getAllStatementLayoutHist: "core/wageprovision/statementlayout/getAllStatementLayoutHist/{0}"
    };

    export function getDataStatement(startYearMonth :any): JQueryPromise<any> {
        let _path = format(paths.getAllStatementLayoutHist, startYearMonth);
        return ajax("pr", _path);
    }
}