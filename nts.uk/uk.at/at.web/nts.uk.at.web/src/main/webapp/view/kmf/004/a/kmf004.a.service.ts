module nts.uk.at.view.kmf004.a.service {
     var paths: any = {
        findAllSpecialHoliday: "shared/specialholiday/findByCid/",
        deleteSpecialHoliday: "shared/specialholiday/delete",
        addSpecialHoliday: "shared/specialholiday/add",
        updateSpecialHoliday: "shared/specialholiday/update",
        findWorkType: "at/share/worktype/findAll"
    }

    export function findAllSpecialHoliday(): JQueryPromise<Array<viewmodel.model.SpecialHolidayDto>> {
        return nts.uk.request.ajax("at",paths.findAllSpecialHoliday);
    }

    export function deleteSpecialHoliday(specialholiday: any) { 
        return nts.uk.request.ajax("at",paths.deleteSpecialHoliday, specialholiday)
    }

    export function addSpecialHoliday(specialholiday: any) {
        return nts.uk.request.ajax("at", paths.addSpecialHoliday, specialholiday);
    }

    export function updateSpecialHoliday(specialholiday: any) {
        return nts.uk.request.ajax("at",paths.updateSpecialHoliday, specialholiday);
    }
    
     export function findWorkType(): JQueryPromise<any> {
        return nts.uk.request.ajax("at",paths.findWorkType);
    }
}