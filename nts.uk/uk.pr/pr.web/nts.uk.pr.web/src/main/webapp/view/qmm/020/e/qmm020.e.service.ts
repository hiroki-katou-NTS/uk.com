module nts.uk.pr.view.qmm020.e {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisClassification: "core/wageprovision/statementbindingsetting/getStateCorrelationHisClassification",
            registerClassification: "core/wageprovision/statementbindingsetting/registerClassification",
            getStateLinkMaster: "core/wageprovision/statementbindingsetting/getStateLinkMaster/{0}/{1}"
        };

        export function getStateCorrelationHisClassification(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisClassification);
        }

        export function registerClassification(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisClassification, data);
        }

        export function getStateLinkMaster(hisId :string, start :number): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkMaster, hisId, start);
            return nts.uk.request.ajax("pr", _path);
        }
    }
}