module nts.uk.hr.view.jhc002.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            
            getAccountLockPolicy:"ctx/sys/gateway/securitypolicy/getAccountLockPolicy",
            getPasswordPolicy:"ctx/sys/gateway/securitypolicy/getPasswordPolicy",
            updateAccountPolicy:"ctx/sys/gateway/securitypolicy/updateAccountPolicy"
            }

     
        
         export function getAccountLockPolicy(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAccountLockPolicy);
        }
    }
}