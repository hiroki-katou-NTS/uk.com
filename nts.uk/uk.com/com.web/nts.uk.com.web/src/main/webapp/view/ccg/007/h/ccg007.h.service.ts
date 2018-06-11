module nts.uk.pr.view.ccg007.h {

    export module service {

        // Service paths.
        var servicePath = {
            submitForgotPass: "ctx/sys/gateway/changepassword/submitforgotpass"
        }

        /**
          * Function is used to check contract.
          */
        export function submitForgotPass(command : ForgotPasswordCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.submitForgotPass, command);
        }
        
        export interface CallerParameter {
            loginId: string;
            contractCode: string;
            url: string;
        }
        
        export class ForgotPasswordCommand {
            url: string;
            oldPassword: string;
            newPassword: string;
            confirmNewPassword: string;
            
            constructor(url: string, oldPassword: string, newPassword: string, confirmNewPassword: string) {
                this.url = url;
                this.oldPassword = oldPassword;
                this.newPassword = newPassword;
                this.confirmNewPassword = confirmNewPassword;
            }
        }
    }
}