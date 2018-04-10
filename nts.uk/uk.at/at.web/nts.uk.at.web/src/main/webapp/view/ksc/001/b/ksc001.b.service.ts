module nts.uk.at.view.ksc001.b {
    export module service {
        var paths = {
            findPeriodById: "ctx/at/shared/workrule/closure/findPeriodById",
            checkThreeMonth: "ctx/at/shared/workrule/closure/checkThreeMonth",
            checkMonthMax: "ctx/at/shared/workrule/closure/checkMonthMax",
            addScheduleExecutionLog: "at/schedule/exelog/add",
            findDailyPatternSetting: "ctx/at/schedule/shift/pattern/daily"
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
                implementAtr: number;
                reCreateAtr: number;
                processExecutionAtr: number;
                reTargetAtr: number;
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
                confirm: boolean;
                createMethodAtr: number;
                copyStartDate: Date;
                employeeIds: string[];
            }

            export interface ScheduleExecutionLogSaveRespone {
                employeeId: string;
                executionId: string;
            }
        }
    }
}
