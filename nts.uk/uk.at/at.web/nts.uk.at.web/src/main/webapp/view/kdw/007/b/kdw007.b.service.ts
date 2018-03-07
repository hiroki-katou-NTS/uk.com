module nts.uk.at.view.kdw007.b.service {
    var paths = {
        getAttendanceItemByCodes: "at/record/divergencetime/AttendanceDivergenceName",
        getAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListByAttendanceAtr/",
        getOptItemByAtr: "at/record/attendanceitem/daily/getattendcomparison/"
    }

    export function getAttendanceItemByCodes(codes) {
        return nts.uk.request.ajax("at", paths.getAttendanceItemByCodes, codes);
    }

    export function getAttendanceItemByAtr(atr) {
        return nts.uk.request.ajax("at", paths.getAttendanceItemByAtr + atr);
    }
    
    export function getOptItemByAtr(atr) {
        return nts.uk.request.ajax("at", paths.getOptItemByAtr + atr);
    }
}