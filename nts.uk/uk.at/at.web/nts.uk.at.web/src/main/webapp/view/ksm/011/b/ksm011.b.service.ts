module nts.uk.at.view.ksm011.b.service {
    /**
     *  Service paths
     */
    var servicePath = {
        getScheDispControl: "ctx/at/schedule/setting/displaycontrol/getScheDispControl",
        saveScheDispControl: "ctx/at/schedule/setting/displaycontrol/saveScheDispControl",
        findAllPublicHD: "at/shared/holidaysetting/config/find",
        findScheFuncControl: "ctx/at/schedule/setting/functioncontrol/findScheFuncControl"
    }  
    
    /**
     *  Get Schedule Display Control
     */
    export function getScheDispControl(): JQueryPromise<ScheDispControlDto> {
        return nts.uk.request.ajax("at", servicePath.getScheDispControl);
    }
    
    /**
     *  Get Schedule Display Control
     */
    export function findAllPublicHD(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", servicePath.findAllPublicHD);
    }
     /**
     *  Get Schedule Display Control
     */
    export function findScheFuncControl(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", servicePath.findScheFuncControl);
    }
    
    /**
     *  Save Schedule Display Control
     */
    export function saveScheDispControl(data: ScheDispControlDto): JQueryPromise<any> {
        return nts.uk.request.ajax("at", servicePath.saveScheDispControl, data);
    }
    
    export interface ScheDispControlDto {
        personSyQualify: string,
        symbolHalfDayAtr: number,
        symbolAtr: number,
        pubHolidayExcessAtr: number,
        pubHolidayShortageAtr: number,
        symbolHalfDayName: string,
        schePerInfoAtr: Array<SchePerInfoAtrDto>,
        scheQualifySet: Array<ScheQualifySetDto> 
    }
    
    export interface SchePerInfoAtrDto {
        personInfoAtr: number
    }
    
    export interface ScheQualifySetDto {
        qualifyCode: string
    }
}
