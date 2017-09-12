module nts.uk.at.view.kmk010.b {
    
    import OvertimeDto = nts.uk.at.view.kmk010.a.service.model.OvertimeDto;
    
    export module service {
        var paths = {
            findAllOvertime : "ctx/at/shared/overtime/findAll",
            saveAllOvertime : "ctx/at/shared/overtime/save",
            findAllOvertimeLanguageName : "ctx/at/shared/overtime/language/name/findAll",
            saveAllOvertimeLanguageName : "ctx/at/shared/overtime/language/name/saveAll",
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
        
        /**
         * find all overtime language name
         */
        export function findAllOvertimeLanguageName(languageId: string): JQueryPromise<model.OvertimeLangNameDto[]> {
            return nts.uk.request.ajax('at', paths.findAllOvertimeLanguageName + '/' + languageId);
        } 
        /**
         * save all overtime language name
         */
        export function saveAllOvertimeLanguageName(overtimeLanguages: model.OvertimeLangNameDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeLanguageName, { overtimeLanguages: overtimeLanguages });
        } 
        
        export module model{
            
            export interface OvertimeLangNameDto {
                name: string;
                languageId: string;
                overtimeNo: number;
            }
        }
        
    }
}