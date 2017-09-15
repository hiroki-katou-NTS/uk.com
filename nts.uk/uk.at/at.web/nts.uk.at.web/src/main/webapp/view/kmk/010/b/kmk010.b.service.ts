module nts.uk.at.view.kmk010.b {
    
    import OvertimeDto = nts.uk.at.view.kmk010.a.service.model.OvertimeDto;
    import OvertimeLangNameDto = nts.uk.at.view.kmk010.a.service.model.OvertimeLangNameDto;
    
    export module service {
        var paths = {
            saveAllOvertime : "ctx/at/shared/overtime/save",
            saveAllOvertimeLanguageName : "ctx/at/shared/overtime/language/name/saveAll"
        }
        
         /**
         * call service save all overtime
         */
        export function saveAllOvertime(overtimes: OvertimeDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertime, { overtimes: overtimes });
        }
        
        /**
         * save all overtime language name
         */
        export function saveAllOvertimeLanguageName(overtimeLanguages: OvertimeLangNameDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeLanguageName, { overtimeLanguages: overtimeLanguages });
        } 
                
    }
}