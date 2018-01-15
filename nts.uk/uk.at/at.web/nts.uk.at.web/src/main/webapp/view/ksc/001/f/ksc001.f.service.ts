module nts.uk.at.view.ksc001.f {
    
    export module service {
        var paths = {
            findScheduleExecutionLogById: "at/schedule/exelog/findById",
            findScheduleExecutionLogInfoById: "at/schedule/exelog/findInfoById",
            executionScheduleExecutionLog: "at/schedule/exelog/execution",
            findAllScheduleErrorLog: "at/schedule/exelog/findAllError",
            exportScheduleErrorLog: "at/schedule/exelog/error/export"
        }
        

        /**
         * call service find ScheduleExecutionLog by id
         */
        export function findScheduleExecutionLogById(executionId: string)
            : JQueryPromise<model.ScheduleExecutionLogDto> {
            return nts.uk.request.ajax('at', paths.findScheduleExecutionLogById + '/' + executionId);
        }
        
        /**
         * executionScheduleExecutionLog
         */
        export function executionScheduleExecutionLog(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.executionScheduleExecutionLog, command);
        }
        
        /**
         * call service find ScheduleExecutionLogInfo by id
         */
        export function findScheduleExecutionLogInfoById(executionId: string)
            : JQueryPromise<model.ScheduleExecutionLogInfoDto> {
            return nts.uk.request.ajax('at', paths.findScheduleExecutionLogInfoById + '/' + executionId);
        }
        /**
         * call service find all ScheduleErrorLog
         */
        export function findAllScheduleErrorLog(executionId: string): JQueryPromise<model.ScheduleErrorLogDto[]> {
            return nts.uk.request.ajax('at', paths.findAllScheduleErrorLog + '/' + executionId);
        }
        
        /**
         * call service export file ScheduleErrorLog 
         */
        export function exportScheduleErrorLog(executionId: string): JQueryPromise<model.ScheduleErrorLogDto[]> {
            return nts.uk.request.exportFile(paths.exportScheduleErrorLog + "/" + executionId);
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
               executionEmployeeId: string;
               period: PeriodDto;
               employeeCode: string;
               employeeName: string;
           }
            
           export interface ScheduleExecutionLogInfoDto {
               totalNumber: number;
               totalNumberCreated: number;
               totalNumberError: number;
           }
            
           export interface ScheduleErrorLogDto {
               errorContent: string;
               executionId: string;
               date: Date;
               employeeId: string;
               employeeCode: string;
               employeeName: string;
           }
        }
    }
}