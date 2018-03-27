module nts.uk.pr.view.ccg007.d {

    export module service {

        // Service paths.
        var servicePath = {
            checkContract: "ctx/sys/gateway/login/checkcontract",
            submitLogin: "ctx/sys/gateway/login/submit/form3",
            getAllCompany: "ctx/sys/gateway/login/getcompany",
            getEmployeeLoginSetting: "ctx/sys/gateway/login/emlogsettingform3",
        }

        /**
         * Function is used to copy new Top Page.
         */
        export function checkContract(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.checkContract, data);
        }

        export function getEmployeeLoginSetting(contractCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getEmployeeLoginSetting + "/" + contractCode);
        }
        
        /**
          * Function is used to copy new Top Page.
          */
        export function submitLogin(data: any): JQueryPromise<string> {
            return nts.uk.request.ajax(servicePath.submitLogin + location.search, data);
        }

        /**
          * Function is used to copy new Top Page.
          */
        export function getAllCompany(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllCompany);
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