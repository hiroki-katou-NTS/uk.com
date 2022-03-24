module nts.uk.pr.view.ccg007.h {

    export module service {

        // Service paths.
        var servicePath = {
            submitForgotPass: "ctx/sys/gateway/changepassword/submitforgotpass",
            getUserNameByURL: "ctx/sys/gateway/changepassword/getUserNameByURL",
        }

        /**
          * Function is used to check contract.
          */
        export function submitForgotPass(command : ForgotPasswordCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.submitForgotPass, command);
        }
        
        export function getUserNameByURL(embeddedId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getUserNameByURL + "/" + embeddedId);
        }

        export class ForgotPasswordCommand {
            embeddedId: string;
            userId: string;
            newPassword: string;
            confirmNewPassword: string;
            
            constructor(embeddedId: string, userId: string, newPassword: string, confirmNewPassword: string) {
                this.embeddedId = embeddedId;
                this.userId = userId;
                this.newPassword = newPassword;
                this.confirmNewPassword = confirmNewPassword;
            }
        }
        
        export interface SubmitData {
            loginId: string;
            password: string;
            contractCode: string;
            contractPassword: string;
        }
    }
}