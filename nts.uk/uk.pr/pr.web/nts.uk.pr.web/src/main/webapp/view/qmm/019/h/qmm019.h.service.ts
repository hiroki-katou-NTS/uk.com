module nts.uk.pr.view.qmm019.h {

    export module service {
        var paths = {
            getAllStatementLayoutAndLastHist: "core/wageprovision/statementlayout/getAllStatementLayoutAndLastHist"
        }

        export function getAllStatementLayoutAndLastHist() : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.getAllStatementLayoutAndLastHist);
        }
    }
}