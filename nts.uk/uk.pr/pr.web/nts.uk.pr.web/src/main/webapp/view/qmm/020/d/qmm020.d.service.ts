module nts.uk.pr.view.qmm020.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisDeparmentById: "core/wageprovision/statementbindingsetting/getStateCorrelationHisDeparmentById",
            registerStateCorrelationHisDeparment: "core/wageprovision/statementbindingsetting/registerStateCorrelationHisDeparment",
        };

        export function getStateCorrelationHisDeparmentById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisDeparmentById);
        }

        export function registerStateCorrelationHisDeparment(data:any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerStateCorrelationHisDeparment,data);
        }

    }
}