module nts.uk.at.view.kmk010.b {
    
    import OvertimeDto = nts.uk.at.view.kmk010.a.service.model.OvertimeDto;
    
    export module service {
        var paths = {
            findAllOvertime : "ctx/at/shared/overtime/findAll",
            saveAllOvertime : "ctx/at/shared/overtime/save"
        }
        
         /**
         * call service find all overtime
         */
        export function findAllOvertime(): JQueryPromise<OvertimeDto[]> {
            return nts.uk.request.ajax('at', paths.findAllOvertime);
        }
         /**
         * call service save all overtime
         */
        export function saveAllOvertime(overtimes: OvertimeDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertime, { overtimes: overtimes });
        }
        
        
        export module model {

            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
                       
        }

    }
}