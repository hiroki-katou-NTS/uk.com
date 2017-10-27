module nts.uk.pr.view.ccg007.c {

    export module service {

        // Service paths.
        var servicePath = {
            checkContract: "ctx/sys/gateway/login/checkcontract",
            submitLogin: "ctx/sys/gateway/login/submit/form2",
            getEmployeeLoginSetting: "ctx/sys/gateway/login/emlogsettingform2"
        }

        /**
          * Function is used to valid contract .
          */
        export function checkContract(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.checkContract, data);
        }
        
        /**
          * Function is used to get employee login setting
          */
        export function getEmployeeLoginSetting(contractCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getEmployeeLoginSetting+"/"+contractCode);
        }

        /**
          * Function is used to submit login.
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