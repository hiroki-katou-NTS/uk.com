module nts.uk.at.view.ksc001.f {
    
    export module service {
        var paths = {
            findScheduleExecutionLogById: "at/schedule/exelog/findById",
        }

        /**
         * call service find ScheduleExecutionLog by id
         */
        export function findScheduleExecutionLogById(executionId: string): JQueryPromise<model.ScheduleExecutionLogDto> {
            return nts.uk.request.ajax('at', paths.findScheduleExecutionLogById + '/' + executionId);
        }

        export module model {
            
            export interface ExecutionContentDto {
                copyStartDate: Date;
                createMethodAtr: number;
                confirm: boolean;
                implementAtr: number;
                processExecutionAtr: number;
                reCreateAtr: number;
                resetMasterInfo: boolean;
                resetAbsentHolidayBusines: boolean;
                resetWorkingHours: boolean;
                resetTimeAssignment: boolean;
                resetDirectLineBounce: boolean;
                resetTimeChildCare: boolean;
            }
            
            export interface ExecutionDateTimeDto{
                executionStartDate: Date;
                executionEndDate: Date;    
            }
            
            export interface PeriodDto{
                startDate: Date;
                endDate: Date;    
            }
            
           export interface ScheduleExecutionLogDto{
               completionStatus: string;
               executionId: string;
               executionContent: ExecutionContentDto;
               executionDateTime: ExecutionDateTimeDto;
               executionEmployeeId: String;
               period: PeriodDto;
               employeeCode: string;
               employeeName: string;
           }
        }
    }
}