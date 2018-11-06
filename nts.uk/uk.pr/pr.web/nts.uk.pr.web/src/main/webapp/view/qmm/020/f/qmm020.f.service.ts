module nts.uk.pr.view.qmm020.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisPosition: "core/wageprovision/statementbindingsetting/getStateCorrelationHisPosition",
            registerCorrelationHisPosition: "core/wageprovision/statementbindingsetting/registerHisPosition",
            getStateLinkSettingMasterPosition: "core/wageprovision/statementbindingsetting/getStateLinkMasterPosition/{0}/{1}",
            getDateBase: "core/wageprovision/statementbindingsetting/getDateBase/{0}"
        };

        export function getStateCorrelationHisPosition(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisPosition);
        }

        export function registerCorrelationHisPosition(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerCorrelationHisPosition, param);
        }

        export function getDateBase(hisId :string): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getDateBase, hisId);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getStateLinkSettingMasterPosition(hisId :string, start :number): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingMasterPosition, hisId, start);
            return nts.uk.request.ajax("pr", _path);
        }
    }
}