module nts.uk.pr.view.qmm020.g {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateCorrelationHisSalary: "core/wageprovision/statementbindingsetting/getStateCorrelationHisSalary",
            registerCorrelationHisSalary: "core/wageprovision/statementbindingsetting/registerHisSalary",
            getStateLinkSettingMasterSalary: "core/wageprovision/statementbindingsetting/getStateLinkMasterSalary/{0}/{1}"
        };

        export function getStateCorrelationHisSalary(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisSalary);
        }

        export function registerCorrelationHisSalary(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerCorrelationHisSalary, param);
        }

        export function getStateLinkSettingMasterSalary(hisId: string, start: number): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingMasterSalary, hisId, start);
            return nts.uk.request.ajax("pr", _path);
        }
    }
}