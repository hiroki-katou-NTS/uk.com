module nts.uk.at.view.kdl009.test {
    export module service {
        var paths: any = {
            getEmployee : "at/request/dialog/employmentsystem/getEmployee/{0}"
        }
        
        export function getEmployee(param: EmployeeParam): JQueryPromise<Array<EmployeeBasicInfoDto>> {
            var path = nts.uk.text.format(paths.getEmployee, param);
            return nts.uk.request.ajax("at", path);
        } 
    }
    
    export interface EmployeeParam {
        employeeIds: Array<string>,
        baseDate: string
    }
    
    export interface EmployeeBasicInfoDto {
        personId: string,
        employeeId: string,
        businessName: string,
        gender: number,
        birthday: string,
        employeeCode: string,
        jobEntryDate: string,
        retirementDate: string
    }
}