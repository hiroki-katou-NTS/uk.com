module nts.uk.at.view.kdw007.b.service {
    var paths = {
        getAttendanceItemByCodes: "at/record/divergencetime/setting/AttendanceDivergenceName",
        getAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListByAttendanceAtr/",
        getOptItemByAtr: "at/record/attendanceitem/daily/getattendcomparison/",
        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName",
        getMonthlyAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListMonthlyByAttendanceAtr/",
        getMonthlyOptItemByAtr: "at/record/attendanceitem/monthly/getattendcomparison/"
    }

    export function getAttendanceItemByCodes(codes, mode) {
        if (mode == 1) //monthly
            return nts.uk.request.ajax("at", paths.getMonthlyAttendanceItemByCodes, codes);
        else
            return nts.uk.request.ajax("at", paths.getAttendanceItemByCodes, codes);
    }

    export function getAttendanceItemByAtr(atr, mode) {
        if (mode == 1) //monthly
            return nts.uk.request.ajax("at", paths.getMonthlyAttendanceItemByAtr + atr);
        else //daily
            return nts.uk.request.ajax("at", paths.getAttendanceItemByAtr + atr);
    }

    export function getOptItemByAtr(atr, mode) {
        if (mode == 1) //monthly
            return nts.uk.request.ajax("at", paths.getMonthlyOptItemByAtr + atr);
        else //daily
            return nts.uk.request.ajax("at", paths.getOptItemByAtr + atr);
    }
}