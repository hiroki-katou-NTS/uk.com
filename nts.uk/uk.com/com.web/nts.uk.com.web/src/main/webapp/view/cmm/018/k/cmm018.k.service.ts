module cmm018.k.service{
    import format = nts.uk.text.format;
    // Service paths.
    var servicePath = {
        searchModeEmployee: "workflow/approvermanagement/workroot/getEmployeesInfo"            
    }    
    /**
     * search data mode employee
     */
    export function searchModeEmployee(input: model.EmployeeSearchInDto) {
        return nts.uk.request.ajax('com', servicePath.searchModeEmployee, input);
    }
    export module model{
        export class EmployeeSearchInDto {
                baseDate: Date;
                workplaceCodes: string[];
            }    
        export class EmployeeSearchDto {
                companyId: string;
                pid: string;
                sid: string;
                scd: string;
                pName: string;

                employeeName: string;

                workplaceCode: string;

                workplaceId: string;

                workplaceName: string;
            }
    }
}