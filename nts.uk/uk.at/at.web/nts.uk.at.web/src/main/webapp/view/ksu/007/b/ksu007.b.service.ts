module nts.uk.at.view.ksu007.b {

    import ScheduleBatchCorrectSettingSave = nts.uk.at.view.ksu007.a.service.model.ScheduleBatchCorrectSettingSave;
    export module service {
        var paths = {
            executionScheduleBatchCorrectSetting: "ctx/at/schedule/processbatch/execution",
            exportScheduleBatchErrorLog: "ctx/at/schedule/processbatch/error/export",
        }


        /**
         * call service execution ScheduleBatchCorrectSetting
         */
        export function executionScheduleBatchCorrectSetting(command: ScheduleBatchCorrectSettingSave): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.executionScheduleBatchCorrectSetting, command);
        }
        
        /**
         * call service export file ScheduleErrorLog 
         */
//        export function exportScheduleErrorLog(executionId: string): JQueryPromise<model.ScheduleErrorLogDto[]> {
//            return nts.uk.request.exportFile(paths.exportScheduleBatchErrorLog + "/" + executionId);
//        }
        
        export module Model {
            export interface ErrorContentDto {
                employeeId: string;
                employeeName: string;
                ymd: string;
                message: string;
            }
        }
    }
}