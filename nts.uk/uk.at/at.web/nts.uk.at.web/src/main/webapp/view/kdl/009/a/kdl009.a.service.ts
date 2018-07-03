module nts.uk.at.view.kdl009.a {
    export module service {
        var paths: any = {
            getEmployee : "at/request/dialog/employmentsystem/getEmployee",
            getAcquisitionNumberRestDays : "at/request/dialog/employmentsystem/getAcquisitionNumberRestDays/{0}/{1}"
        }
          
        export function getEmployee(param: EmployeeParam): JQueryPromise<Array<EmployeeBasicInfoDto>> {
            return nts.uk.request.ajax("at", paths.getEmployee, param);
        } 

        
        export function getAcquisitionNumberRestDays(employeeId: string, baseDate: string): JQueryPromise<any> {
            var path = nts.uk.text.format(paths.getAcquisitionNumberRestDays, employeeId, baseDate);
            return nts.uk.request.ajax(path);
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