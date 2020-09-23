module nts.uk.at.view.ksu001.jd.service {
    var paths: any = { 
        getShiftPaletteByCompany: "at/schedule/shift/management/getShiftPaletteByCompany",
        getShiftPaletteByWP: "at/schedule/shift/management/shiftpalletorg/getShiftPaletteByWP",
        getShiftPaletteByWPG: "at/schedule/shift/management/shiftpalletorg/getShiftPaletteByWPG",
        duplicateComShiftPalet: "at/schedule/shift/management/duplicateComShiftPalet",
        duplicateOrgShiftPalet: "at/schedule/shift/management/shiftpalletorg/duplicateOrgShiftPalet"
    }

    export function getShiftPaletteByCompany(): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.getShiftPaletteByCompany);
    }

    export function getShiftPaletteByWP(workplaceId: string): JQueryPromise<any> {
     
        let para = {id: workplaceId};
        return nts.uk.request.ajax(paths.getShiftPaletteByWP, para);
    }

    export function getShiftPaletteByWPG(WPGId: string): JQueryPromise<any> {
     
        let para = {id: WPGId};
        return nts.uk.request.ajax(paths.getShiftPaletteByWPG, para);
    }

    export function duplicateComShiftPalet(command: any): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.duplicateComShiftPalet, command);
    }

    export function duplicateOrgShiftPalet(command: any): JQueryPromise<any> {
     
        return nts.uk.request.ajax(paths.duplicateOrgShiftPalet, command);
    }

}