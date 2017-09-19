module nts.uk.at.view.kmk010.c {
    
    import OutsideOTBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OutsideOTBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    import OutsideOTBRDItemLangDto = nts.uk.at.view.kmk010.a.service.model.OutsideOTBRDItemLangDto;
    
    export module service {
        var paths = {
            findAllProductNumber : "ctx/at/shared/overtime/breakdown/findAll/productNumber",
            saveAllOutsideOTBRDItem : "ctx/at/shared/overtime/breakdown/save",
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
        export function saveAllOutsideOTBRDItem(OutsideOTBRDItems: OutsideOTBRDItemDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOutsideOTBRDItem, { OutsideOTBRDItems: OutsideOTBRDItems });
        }
        
        /**
         * save all overtime language breakdown item
         */
        export function saveAllOvertimeLanguageBRDItem(overtimeLanguages: OutsideOTBRDItemLangDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeLanguageBRDItem, { overtimeLanguages: overtimeLanguages });
        } 
        
    }
}