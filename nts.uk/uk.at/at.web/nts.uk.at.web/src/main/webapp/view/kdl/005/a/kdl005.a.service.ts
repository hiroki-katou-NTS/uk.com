module nts.uk.at.view.kdl005.a {
    export module service {
        var paths: any = {
            getEmployeeList : "at/request/dialog/employmentsystem/getEmployeeList",
            getDetailsConfirm : "at/request/dialog/employmentsystem/getDetailsConfirm/{0}/{1}"
        }
                
        export function getEmployeeList(param: EmployeeParam): JQueryPromise<Array<EmployeeBasicInfoDto>> {
            return nts.uk.request.ajax("at", paths.getEmployeeList, param);
        } 
        
        export function getDetailsConfirm(employeeId: string, baseDate: string): JQueryPromise<any> {
            var path = nts.uk.text.format(paths.getDetailsConfirm, employeeId, baseDate);
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