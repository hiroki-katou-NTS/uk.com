
module nts.uk.at.view.kdl029.a.service {
    const paths: any = { 
        findAllEmploymentSystem: "at/request/application/employment/system",
        findByEmployee: "at/request/application/employment/getByEmployee"
    }
    
    export function findAllEmploymentSystem(param: any): JQueryPromise<EmpRsvLeaveInforDto> {
        return nts.uk.request.ajax(paths.findAllEmploymentSystem, param);
    }

    export function findByEmployee(param: any): JQueryPromise<EmpRsvLeaveInforDto> {
        return nts.uk.request.ajax(paths.findByEmployee, param);
    }

    export interface EmpRsvLeaveInforDto {
        employeeInfors: EmployeeInfoImport[],
	    rsvLeaManaImport: RsvLeaManagerImport,
	    employeeCode: string,
	    employeeName: string,
        yearResigName: string,
        retentionManage: boolean
    }

    export interface EmployeeInfoImport {
	    sid: string,
	    scd: string,
	    bussinessName: string
    }

    export interface RsvLeaManagerImport {
        reserveLeaveInfo: RsvLeaveInfoImport,
        grantRemainingList: RsvLeaGrantRemainingImport[],
	    tmpManageList: TmpRsvLeaveMngImport[]
    }

    export interface RsvLeaveInfoImport {
        befRemainDay: number,
        aftRemainDay: number,
        grantDay: number,
        remainingDays: number
    }

    export interface RsvLeaGrantRemainingImport {
        grantDate: string,
        deadline: string,
        grantNumber: number,
        usedNumber: number,
        remainingNumber: number,
        expiredInCurrentMonth: boolean
    }

    export interface TmpRsvLeaveMngImport {
        ymd: string,
	    creatorAtr: string,
	    useDays: number
    }
    
}