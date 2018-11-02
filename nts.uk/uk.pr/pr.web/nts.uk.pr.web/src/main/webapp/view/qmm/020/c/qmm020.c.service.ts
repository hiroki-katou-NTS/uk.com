module nts.uk.pr.view.qmm020.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisEmployeeById: "core/wageprovision/statementbindingsetting/getStateCorrelationHisEmployeeById",
            registerStateCorrelationHisEmployee: "core/wageprovision/statementbindingsetting/registerStateCorrelationHisEmployee",
            findEmploymentAll: "core/wageprovision/statementbindingsetting/findEmploymentAll",
        };

        export function getStateCorrelationHisEmployeeById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisEmployeeById);
        }

        export function registerStateCorrelationHisEmployee(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerStateCorrelationHisEmployee,data);
        }

        export function findEmploymentAll(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.findEmploymentAll);
        }

    }
}