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
        //Export common excel
        export function saveAsExcel(languageId: String): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let programName = program[1]!=null?program[1]:"";
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "SecuritySetting", domainType: "CAS003" + programName, languageId: languageId, reportType: 0 });
        }
        
    }
}
