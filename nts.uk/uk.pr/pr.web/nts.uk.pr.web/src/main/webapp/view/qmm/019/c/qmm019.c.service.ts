module nts.uk.pr.view.qmm019.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getStatementLayoutAndHistById: "core/wageprovision/statementlayout/getStatementLayoutAndHistById/{0}/{1}",
            getStatementLayoutAndLastHist: "core/wageprovision/statementlayout/getStatementLayoutAndLastHist/{0}",
            updateStatementLayoutHist: "core/wageprovision/statementlayout/updateStatementLayoutHist",
            deleteStatementLayoutHist: "core/wageprovision/statementlayout/deleteStatementLayoutHist"
        }

        export function getStatementLayoutAndHistById(code: string, histId: string): JQueryPromise<any> {
            let _path = format(paths.getStatementLayoutAndHistById, code, histId);
            return ajax('pr', _path);
        }

        export function getStatementLayoutAndLastHist(code: string): JQueryPromise<any> {
            let _path = format(paths.getStatementLayoutAndLastHist, code);
            return ajax('pr', _path);
        }

        export function updateStatementLayoutHist(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.updateStatementLayoutHist, command);
        }

        export function deleteStatementLayoutHist(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.deleteStatementLayoutHist, command);
        }
    }
}