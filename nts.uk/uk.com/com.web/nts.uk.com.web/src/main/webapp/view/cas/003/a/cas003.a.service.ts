module nts.uk.com.view.cas003.a {
    export module service {
        var paths: any = {
            
            getAccountLockPolicy:"ctx/sys/gateway/securitypolicy/getAccountLockPolicy",
            getPasswordPolicy:"ctx/sys/gateway/securitypolicy/getPasswordPolicy",
            updateAccountPolicy:"ctx/sys/gateway/securitypolicy/updateAccountPolicy"
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
