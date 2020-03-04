module nts.uk.pr.view.qmm020.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateLinkSettingCompanyById: "core/wageprovision/statementbindingsetting/getStateLinkSettingCompanyById/{0}/{1}",
            getStateCorrelationHisCompanyById: "core/wageprovision/statementbindingsetting/getStateCorrelationHisCompanyById",
            registerStateLinkSettingCompany: "core/wageprovision/statementbindingsetting/registerStateLinkSettingCompany",
        };

        export function getStateLinkSettingCompanyById(hisId: String,startYearMonth: number): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingCompanyById,hisId,startYearMonth);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getStateCorrelationHisCompanyById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisCompanyById);
        }

        export function registerStateLinkSettingCompany(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerStateLinkSettingCompany, data);
        }
    }
}