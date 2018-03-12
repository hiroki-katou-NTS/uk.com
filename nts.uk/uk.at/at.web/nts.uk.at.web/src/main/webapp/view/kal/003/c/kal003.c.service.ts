module nts.uk.at.view.kal003.c.service {
    var paths = {
        getAttendanceItemByCodes: "at/record/divergencetime/AttendanceDivergenceName",
        getAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListByAttendanceAtr/"
    }

    export function getAttendanceItemByCodes(codes) {
        return nts.uk.request.ajax("at", paths.getAttendanceItemByCodes, codes);
    }

    export function getAttendanceItemByAtr(atr) {
        return nts.uk.request.ajax("at", paths.getAttendanceItemByAtr + atr);
    }
}