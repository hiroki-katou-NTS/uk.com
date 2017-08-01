module nts.uk.pr.view.ccg007.c {

    export module service {

        // Service paths.
        var servicePath = {
            getContractAuth: "ctx/sys/gateway/login/checkcontract1",
            submitLogin: "ctx/sys/gateway/login/submit/form1"
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