module nts.uk.at.view.ksc001.b {
    export module service {
        var paths = {
            findPeriodById: "ctx/at/shared/workrule/closure/findPeriodById",
            checkThreeMonth: "ctx/at/shared/workrule/closure/checkThreeMonth",
            checkMonthMax: "ctx/at/shared/workrule/closure/checkMonthMax",
            addScheduleExecutionLog: "at/schedule/exelog/add",
            findDailyPatternSetting: "ctx/at/schedule/shift/pattern/daily",
            getMonthlyPattern: 'monthly/get/all',
            getInitialDate: 'get/initial/information',
            getEmployeeListAfterFilter: 'get/employee/listemployid'
        }

        /**
         * call service find by period closure 
         */
        export function findPeriodById(closureId: number): JQueryPromise<model.PeriodDto> {
            return nts.uk.request.ajax('at', paths.findPeriodById + '/' + closureId);
        }

        /**
         * call service check three month by base date
         */
        export function checkThreeMonth(baseDate: Date): JQueryPromise<boolean> {
            return nts.uk.request.ajax('at', paths.checkThreeMonth, { baseDate: baseDate });
        }

        /**
         * call service check max month by base date
         */
        export function checkMonthMax(baseDate: Date): JQueryPromise<boolean> {
            return nts.uk.request.ajax('at', paths.checkMonthMax, { baseDate: baseDate });
        }
        /**
         * call service add ScheduleExecutionLog
         */
        export function addScheduleExecutionLog(command: model.ScheduleExecutionLogSaveDto)
            : JQueryPromise<model.ScheduleExecutionLogSaveRespone> {
            return nts.uk.request.ajax('at', paths.addScheduleExecutionLog, command);
        }


        /**
         * call service to get start & end date
         */
        export function getInitialDate(): JQueryPromise<model.PeriodDto> {
            return nts.uk.request.ajax('at', paths.getInitialDate);
        }

        /**
         * call service to get the monthly patterns list
         */
        export function getMonthlyPattern(): JQueryPromise<model.PeriodDto> {
            return nts.uk.request.ajax('at', paths.getMonthlyPattern);
        }

        /**
         * call service to get the monthly patterns list
         */
        export function getEmployeeListAfterFilter( data: model.ListEmployeeIds ): JQueryPromise<model.PeriodDto> {
            return nts.uk.request.ajax('at', paths.getEmployeeListAfterFilter, data);
        }

        export module model {

            export interface NtsWizardStep {
                content: string;
            }

            export interface PeriodDto {
                startDate: Date;
                endDate: Date;
            }
            export interface UserInfoDto {
                companyId: string;
                employeeId: string;
            }

            export interface ScheduleExecutionLogSaveDto {
                periodStartDate: Date;
                periodEndDate: Date;
	            creationType: number;
	            reTargetAtr: boolean;
	            referenceMaster: number;
	            reTargetTransfer: boolean;
	            reTargetLeave: boolean;
	            reTargetShortWork: boolean;
	            reTargetLaborChange: boolean;
	            reOverwriteConfirmed: boolean;
	            reOverwriteRevised: boolean;
	            monthlyPatternId: string; //monthlyPatternCode;
	            beConfirmed: boolean; //confirm
	            creationMethod: number; //createMethodAtr
	            copyStartYmd: Date; //copyStartDate
                employeeIds: string[];
	            employeeIdLogin: string
	            implementAtr: number;
	            reCreateAtr: number;
				processExecutionAtr: number;
	            confirm: boolean,
				resetWorkingHours: boolean;
				resetMasterInfo: boolean;
				reTimeAssignment: boolean;
				reConverter: boolean;
				reStartEndTime: boolean;
				reEmpOffWork: boolean;
				reShortTermEmp: boolean;
				reWorkTypeChange: boolean;
				reDirectBouncer: boolean;
				protectHandCorrect: boolean;
	            createMethodAtr: number;
            }

            export interface ScheduleExecutionLogSaveRespone {
                employeeId: string;
                executionId: string;
            }

            export interface ListEmployeeIds {

                listEmployeeId: Array<string>;
                /** 異動者. */
                transfer: boolean;
                /** 休職休業者. */
                leaveOfAbsence: boolean;
                /** 短時間勤務者. */
                shortWorkingHours: boolean;
                /** 労働条件変更者. */
                changedWorkingConditions: boolean;
                startDate : string; //YYYY/MM/DD
                endDate : string; //YYYY/MM/DD
            }
        }
    }
}
