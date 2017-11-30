module nts.uk.at.view.ksu007.a {
    export module service {
        var paths = {
           checkScheduleBatchCorrectSettingSave: "ctx/at/schedule/processbatch/check"
        }
        
        /**
         * call service check ScheduleBatchCorrectSetting save
         */
        export function checkScheduleBatchCorrectSettingSave(dto: model.ScheduleBatchCorrectSettingSave): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.checkScheduleBatchCorrectSettingSave, dto);
        }
        
        
        export module model {

            export interface ScheduleBatchCorrectSettingSave {
                worktypeCode: string;

                employeeId: string;

                endDate: Date;

                startDate: Date;
                
                worktimeCode: string;

                employeeIds: string[];
            }
            
        }

    }
}
