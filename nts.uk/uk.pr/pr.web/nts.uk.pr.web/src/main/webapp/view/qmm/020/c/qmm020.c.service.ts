module nts.uk.pr.view.qmm020.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisEmployeeById: "core/wageprovision/statementbindingsetting/getStateCorrelationHisEmployeeById",
            registerStateCorrelationHisEmployee: "core/wageprovision/statementbindingsetting/registerStateCorrelationHisEmployee",
            getStateLinkSettingMasterByHisId: "core/wageprovision/statementbindingsetting/getStateLinkSettingMasterByHisId/{0}",
        };

        export function getStateCorrelationHisEmployeeById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisEmployeeById);
        }

        export function registerStateCorrelationHisEmployee(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerStateCorrelationHisEmployee,data);
        }
        export function getStateLinkSettingMasterByHisId(hisId:any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingMasterByHisId,hisId);
            return nts.uk.request.ajax("pr", _path);
        }

    }
}