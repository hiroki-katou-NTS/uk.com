module nts.uk.pr.view.ccg007.b {

    export module service {

        // Service paths.
        var servicePath = {
            getContractAuth: "ctx/at/shared/login/find",
            submitLogin: "ctx/at/shared/login/submit"
        }

        /**
          * Function is used to copy new Top Page.
          */
        export function getLoginForm(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getContractAuth);
        }

        /**
          * Function is used to copy new Top Page.
          */
        export function submitLogin(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.submitLogin, data);
        }

        export interface SystemConfigDto {
            installForm: number;
        }
        export interface ContractDto {
            password: string;
            contractCode: string;
            startDate: string;
            endDate: string;
        }
    }
}