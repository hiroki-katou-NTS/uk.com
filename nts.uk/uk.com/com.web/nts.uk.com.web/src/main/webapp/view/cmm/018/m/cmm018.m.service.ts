module nts.uk.com.view.cmm018.m {
    export module service {
         // Service paths.
        var servicePath = {
            searchModeEmployee: "workflow/approvermanagement/workroot/testInUnregistry"            
        } 
        export function searchModeEmployee(data: MasterDto) {
            return nts.uk.request.ajax('com', servicePath.searchModeEmployee, baseDate);
        }
        export class MasterDto{
            baseDate: Date;
            chkCompany: boolean;
            chkWorkplace: boolean;
            chkPerson: boolean;    
        }
    }
    
    
}
