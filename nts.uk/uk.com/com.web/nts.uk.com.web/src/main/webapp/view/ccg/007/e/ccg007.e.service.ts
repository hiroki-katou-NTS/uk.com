module nts.uk.pr.view.ccg007.e {

    export module service {

        // Service paths.
        var servicePath = {
            submitChangePass: "ctx/sys/gateway/changepassword/submitchangepass"
        }

        /**
          * Function is used to check contract.
          */
        export function submitChangePass(data : ChangePasswordCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.submitChangePass, data);
        }
        
        export interface CallerParameter {
            loginId: string;
            contractCode: string;
        }
        
        export class ChangePasswordCommand {
            oldPassword: string;
            newPassword: string;
            confirmNewPassword: string;
            
            constructor(oldPassword: string, newPassword: string, confirmNewPassword: string) {
                this.oldPassword = oldPassword;
                this.newPassword = newPassword;
                this.confirmNewPassword = confirmNewPassword;
            }
        }
    }
}