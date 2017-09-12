module nts.uk.at.view.kmk010.c {
    
    import OvertimeBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OvertimeBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    
    export module service {
        var paths = {
            findAllProductNumber : "ctx/at/shared/overtime/breakdown/findAll/productNumber",
            findAllOvertimeBRDItem : "ctx/at/shared/overtime/breakdown/findAll",
            saveAllOvertimeBRDItem : "ctx/at/shared/overtime/breakdown/save"
        }
        
         /**
         * call service find all product number
         */
        export function findAllProductNumber(): JQueryPromise<EnumConstantDto[]> {
            return nts.uk.request.ajax('at', paths.findAllProductNumber);
        }
         /**
         * call service find all overtime breakdown item
         */
        export function findAllOvertimeBRDItem(): JQueryPromise<OvertimeBRDItemDto[]> {
            return nts.uk.request.ajax('at', paths.findAllOvertimeBRDItem);
        }
         /**
         * call service save all overtime breakdown item
         */
        export function saveAllOvertimeBRDItem(overtimeBRDItems: OvertimeBRDItemDto[]): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveAllOvertimeBRDItem, { overtimeBRDItems: overtimeBRDItems });
        }
        

    }
}