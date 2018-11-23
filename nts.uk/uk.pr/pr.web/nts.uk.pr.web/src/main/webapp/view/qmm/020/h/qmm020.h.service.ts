module nts.uk.pr.view.qmm020.h {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmployee: "workflow/approvermanagement/workroot/getInforPsLogin",
            getStateCorrelationHisIndividual: "core/wageprovision/statementbindingsetting/getStateCorrelationHisIndividual/{0}",
            getWpName: "screen/com/kcp010/getLoginWorkPlace",
            getStateLinkSettingMasterIndividual: "core/wageprovision/statementbindingsetting/getStateLinkSettingMasterIndividual/{0}/{1}/{2}",
            registerHisIndividual: "core/wageprovision/statementbindingsetting/registerHisIndividual"
        };

        export function getEmployee(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getEmployee);
        }

        export function registerHisIndividual(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.registerHisIndividual, param);
        }

        export function getWpName(): JQueryPromise<any> {
            return ajax("com", path.getWpName);
        }

        export function getStateLinkSettingMasterIndividual(empId: string, hisId: string, start: number): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateLinkSettingMasterIndividual,empId, hisId, start);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getStateCorrelationHisIndividual(empId: string): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getStateCorrelationHisIndividual, empId);
            return nts.uk.request.ajax("pr", _path);
        }

    }
}