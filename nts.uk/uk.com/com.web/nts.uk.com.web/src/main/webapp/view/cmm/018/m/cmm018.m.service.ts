module nts.uk.com.view.cmm018.m {
    export module service {
         // Service paths.
        var servicePath = {
            searchModeEmployee: "workflow/approvermanagement/workroot/testMasterDat"            
        } 
        export function searchModeEmployee(data: MasterDto) {
            return nts.uk.request.ajax('com', servicePath.searchModeEmployee, data);
        }
        export class MasterDto{
            baseDate: Date;
            chkCompany: boolean;
            chkWorkplace: boolean;
            chkPerson: boolean;
            constructor(baseDate: Date, chkCompany: boolean, chkWorkplace: boolean, chkPerson: boolean){
                this.baseDate = baseDate;
                this.chkCompany = chkCompany;
                this.chkWorkplace = chkWorkplace;
                this.chkPerson = chkPerson;    
            }
        }
    }
    
    
}
