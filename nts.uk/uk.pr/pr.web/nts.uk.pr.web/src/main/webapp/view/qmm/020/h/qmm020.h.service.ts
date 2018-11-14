module nts.uk.pr.view.qmm020.h {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmployee: "workflow/approvermanagement/workroot/getInforPsLogin",
            getStateCorrelationHisIndividual: "core/wageprovision/statementbindingsetting/getStateCorrelationHisIndividual",
            getWpName: "screen/com/kcp010/getLoginWorkPlace",
            getStateLinkSettingMasterIndividual: "core/wageprovision/statementbindingsetting/getStateLinkSettingMasterIndividual/{0}/{1}"
        };

        export function getEmployee(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getEmployee);
        }

        export function getWpName(): JQueryPromise<any> {
            return ajax("com", path.getWpName);
        }

        export function getStateLinkSettingMasterIndividual(hisId: string, start: number): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingMasterIndividual, hisId, start);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getStateCorrelationHisIndividual(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateCorrelationHisIndividual);
        }

    }
}