module nts.uk.at.view.kmk010.c {
    
    import OutsideOTBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OutsideOTBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    import OutsideOTBRDItemLangDto = nts.uk.at.view.kmk010.a.service.model.OutsideOTBRDItemLangDto;
    
    export module service {
        var paths = {
            findAllProductNumber : "ctx/at/shared/outsideot/breakdown/findAll/productNumber",
            saveAllOutsideOTBRDItem : "ctx/at/shared/outsideot/breakdown/save",
            saveAllOvertimeLanguageBRDItem : "ctx/at/shared/outsideot/breakdown/language/saveAll"
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
        export function saveAllOutsideOTBRDItem(overtimeBRDItems: OutsideOTBRDItemDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOutsideOTBRDItem, { overtimeBRDItems: overtimeBRDItems });
        }
        
        /**
         * save all overtime language breakdown item
         */
        export function saveAllOvertimeLanguageBRDItem(overtimeLanguages: OutsideOTBRDItemLangDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeLanguageBRDItem, { overtimeLanguages: overtimeLanguages });
        } 
        
    }
}