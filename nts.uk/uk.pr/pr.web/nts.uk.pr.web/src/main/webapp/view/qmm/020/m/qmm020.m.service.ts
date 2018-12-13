module nts.uk.pr.view.qmm020.m.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths : any = {
        getAllStatementLayoutHist: "core/wageprovision/statementlayout/getAllStatementLayoutHist/{0}/{1}"
    };

    export function getDataStatement(startYearMonth :any, endYearMonth): JQueryPromise<any> {
        let _path = format(paths.getAllStatementLayoutHist, startYearMonth,endYearMonth);
        return ajax("pr", _path);
    }
}