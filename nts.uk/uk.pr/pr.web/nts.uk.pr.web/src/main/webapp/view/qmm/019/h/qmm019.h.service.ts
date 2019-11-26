module nts.uk.pr.view.qmm019.h {

    export module service {
        let paths = {
            getAllStatementLayoutAndLastHist: "core/wageprovision/statementlayout/getAllStatementLayoutAndLastHist",
            addStatementLayout: "core/wageprovision/statementlayout/addStatementLayout"
        };

        export function getAllStatementLayoutAndLastHist() : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.getAllStatementLayoutAndLastHist);
        }

        export function addStatementLayout(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.addStatementLayout, command);
        }
    }
}