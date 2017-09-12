module nts.uk.com.view.cmm018.l.service {
    // Service paths.
    var servicePath = {
        searchModeEmployee: "workflow/approvermanagement/workroot/testInUnregistry"            
    } 
    export function searchModeEmployee(baseDate: string) {
        return nts.uk.request.ajax('com', servicePath.searchModeEmployee, baseDate);
    }   
}
