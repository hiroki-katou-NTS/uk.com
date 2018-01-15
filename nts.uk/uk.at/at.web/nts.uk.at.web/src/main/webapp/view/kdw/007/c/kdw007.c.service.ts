module nts.uk.at.view.kdw007.c.service {
    var paths = {
        getAttendanceItemByCodes: "at/record/divergencetime/AttendanceDivergenceName"
    }
    
    export function getAttendanceItemByCodes(codes) {
        return nts.uk.request.ajax("at", paths.getAttendanceItemByCodes, codes);
    }
}