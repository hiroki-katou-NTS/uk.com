module nts.uk.com.view.cas003.a {
    export module service {
        var paths: any = {
            
            getAccountLockPolicy:"ctx/sys/gateway/login/getAccountLockPolicy",
            getPasswordPolicy:"ctx/sys/gateway/login/getPasswordPolicy",
            updateAccountPolicy:"ctx/sys/gateway/login/updateAccountPolicy"
            }

     
        
         export function getAccountLockPolicy(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAccountLockPolicy);
        }
           export function getPasswordPolicy(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getPasswordPolicy);
        }
         export function updateAccountPolicy(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateAccountPolicy, command);
        }
        
    }
}
