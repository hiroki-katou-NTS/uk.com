module nts.uk.pr.view.qmm020.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateLinkSettingCompanyById: "core/wageprovision/statementbindingsetting/getStateLinkSettingCompanyById/{0}",
            getStateCorrelationHisCompanyById: "core/wageprovision/statementbindingsetting/getStateCorrelationHisCompanyById",
            register: "core/wageprovision/statementbindingsetting/register",
        };

        export function getStateLinkSettingCompanyById(hisId: String): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingCompanyById,hisId);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getStateCorrelationHisCompanyById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisCompanyById);
        }

        export function register(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, data);
        }
    }
}