module nts.uk.pr.view.ccg007.g {

    export module service {

        // Service paths.
        var servicePath = {
            submitSendMail: "ctx/sys/gateway/sendmail/submit"
        }

        /**
          * Function is used to check contract.
          */
        export function submitSendMail(data : CallerParameter): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.submitSendMail, data);
        }
        
        export interface CallerParameter {
            companyCode: string;
            companyName: string;
            employeeCode : string;
            contractCode: string;
        }
    }
}