module nts.uk.pr.view.qmm020.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisPosition: "core/wageprovision/statementbindingsetting/getStateCorrelationHisClassification"
        };

        export function getStateCorrelationHisPosition(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisPosition);
        }
    }
}