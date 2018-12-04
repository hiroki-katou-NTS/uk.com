module nts.uk.pr.view.qmm019.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        let paths = {
            getAllStatementLayoutAndHist: "core/wageprovision/statementlayout/getAllStatementLayoutAndHist",
            getStatementLayoutHistData: "core/wageprovision/statementlayout/getStatementLayoutHistData/{0}/{1}",
            getInitStatementLayoutHistData: "core/wageprovision/statementlayout/getInitStatementLayoutHistData/{0}/{1}/{2}/{3}/{4}",
            updateStatementLayoutHistData: "core/wageprovision/statementlayout/updateStatementLayoutHistData"
        };

        export function getAllStatementLayoutAndHist() : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.getAllStatementLayoutAndHist);
        }

        export function getStatementLayoutHistData(code: string, histId: string): JQueryPromise<any> {
            let _path = format(paths.getStatementLayoutHistData, code, histId);
            return ajax('pr', _path);
        }

        export function getInitStatementLayoutHistData(statementCode: string, histId: string, startMonth: number, itemHistoryDivision: number, layoutPattern: number): JQueryPromise<any> {
            let _path = format(paths.getInitStatementLayoutHistData, statementCode, histId, startMonth, itemHistoryDivision, layoutPattern);
            return ajax('pr', _path);
        }

        export function updateStatementLayoutHistData(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.updateStatementLayoutHistData, command);
        }
    }
}