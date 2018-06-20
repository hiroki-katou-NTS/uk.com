module nts.uk.pr.view.ccg007.h {

    export module service {

        // Service paths.
        var servicePath = {
            submitForgotPass: "ctx/sys/gateway/changepassword/submitforgotpass",
            getUserNameByLoginId: "ctx/sys/gateway/changepassword/getUserNameByLoginId"
        }

        /**
          * Function is used to check contract.
          */
        export function submitForgotPass(command : ForgotPasswordCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.submitForgotPass, command);
        }
        
        export function getUserNameByLoginId(contractCode : string, loginId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getUserNameByLoginId + "/" + contractCode + "/" + loginId);
        }
        
        export interface CallerParameter {
            loginId: string;
            contractCode: string;
            url: string;
        }
        
        export class ForgotPasswordCommand {
            url: string;
            userId: string;
            newPassword: string;
            confirmNewPassword: string;
            
            constructor(url: string, userId: string, newPassword: string, confirmNewPassword: string) {
                this.url = url;
                this.userId = userId;
                this.newPassword = newPassword;
                this.confirmNewPassword = confirmNewPassword;
            }
        }
    }
}