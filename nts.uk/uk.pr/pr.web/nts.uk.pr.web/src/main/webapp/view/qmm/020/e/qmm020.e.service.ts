module nts.uk.pr.view.qmm020.e {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisClassification: "core/wageprovision/statementbindingsetting/getStateCorrelationHisClassification",
            getStateLinkSettingMaster: "core/wageprovision/statementbindingsetting/getStateCorrelationHisClassification/{0}",
            register: "core/wageprovision/statementbindingsetting"
        };

        export function getStateCorrelationHisClassification(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisClassification);
        }

    /*    export function getStateLinkSettingMaster(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, data);
        }*/

        export function getStateLinkSettingMaster(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingMaster, param);
            return nts.uk.request.ajax("pr", _path);
        }
    }
}