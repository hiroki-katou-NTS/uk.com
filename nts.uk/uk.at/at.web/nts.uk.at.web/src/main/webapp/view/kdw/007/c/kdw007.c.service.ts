module nts.uk.at.view.kdw007.c.service {
    var paths = {
        getDailyAttendanceItemByCodes: "at/record/divergencetime/AttendanceDivergenceName",
        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/getMonthlyAttendanceDivergenceName"
    }
    
    export function getDailyAttendanceItemByCodes(codes) {
        return nts.uk.request.ajax("at", paths.getDailyAttendanceItemByCodes, codes);
    }
    
    export function getMonthlyAttendanceItemByCodes(codes) {
        return nts.uk.request.ajax("at", paths.getMonthlyAttendanceItemByCodes, codes);
    }
    
}