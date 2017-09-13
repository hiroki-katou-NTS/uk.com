module nts.uk.at.view.kmk010.c {
    
    import OvertimeBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OvertimeBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    import OvertimeLangBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OvertimeLangBRDItemDto;
    
    export module service {
        var paths = {
            findAllProductNumber : "ctx/at/shared/overtime/breakdown/findAll/productNumber",
            saveAllOvertimeBRDItem : "ctx/at/shared/overtime/breakdown/save",
            saveAllOvertimeLanguageBRDItem : "ctx/at/shared/overtime/breakdown/language/saveAll"
        }
        
         /**
         * call service find all product number
         */
        export function findAllProductNumber(): JQueryPromise<EnumConstantDto[]> {
            return nts.uk.request.ajax('at', paths.findAllProductNumber);
        }
         
         /**
         * call service save all overtime breakdown item
         */
        export function saveAllOvertimeBRDItem(overtimeBRDItems: OvertimeBRDItemDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeBRDItem, { overtimeBRDItems: overtimeBRDItems });
        }
        
        /**
         * save all overtime language breakdown item
         */
        export function saveAllOvertimeLanguageBRDItem(overtimeLanguages: OvertimeLangBRDItemDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeLanguageBRDItem, { overtimeLanguages: overtimeLanguages });
        } 
        
    }
}