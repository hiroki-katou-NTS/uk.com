module nts.uk.at.view.ksu001.jd.service {
    var paths: any = { 
        getShiftPaletteByCompany: "at/schedule/shift/management/getShiftPaletteByCompany",
        getShiftPaletteByWP: "at/schedule/shift/management/shiftpalletorg/getShiftPaletteByWP/{0}",
        getShiftPaletteByWPG: "at/schedule/shift/management/shiftpalletorg/getShiftPaletteByWPG/{0}",
        duplicateComShiftPalet: "at/schedule/shift/management/duplicateComShiftPalet",
        duplicateOrgShiftPalet: "at/schedule/shift/management/shiftpalletorg/duplicateOrgShiftPalet"
    }

    export function getShiftPaletteByCompany(): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.getShiftPaletteByCompany);
    }

    export function getShiftPaletteByWP(workplaceId: string): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.getShiftPaletteByWP, workplaceId);
    }

    export function getShiftPaletteByWPG(WPGId: string): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.getShiftPaletteByWPG, WPGId);
    }

    export function duplicateComShiftPalet(command: any): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.duplicateComShiftPalet, command);
    }

    export function duplicateOrgShiftPalet(command: any): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.duplicateOrgShiftPalet, command);
    }

}