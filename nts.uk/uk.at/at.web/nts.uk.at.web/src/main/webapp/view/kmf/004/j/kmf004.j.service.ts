module nts.uk.at.view.kmf004.j.service {
    var paths: any = {
        getAllAbsenceFrame: "at/share/worktype/absenceframe/findAll",
        getAllSpecialHolidayFrame: "at/share/worktype/specialholidayframe/findAll",
        getNursingLeaveSetting: "ctx/at/share/vacation/setting/nursingleave/find/setting"
    }
    
    export function getAllAbsenceFrame(): JQueryPromise<Array<any>> {
        var path = paths.getAllAbsenceFrame;
        return nts.uk.request.ajax("at", path);
    }
    
    export function getAllSpecialHolidayFrame(): JQueryPromise<Array<any>> {
        var path = paths.getAllSpecialHolidayFrame;
        return nts.uk.request.ajax("at", path);
    }
    
    export function getNursingLeaveSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getNursingLeaveSetting);
    }
}