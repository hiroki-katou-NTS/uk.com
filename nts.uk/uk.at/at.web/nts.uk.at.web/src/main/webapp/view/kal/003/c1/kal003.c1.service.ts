module nts.uk.at.view.kal003.c1.service {
    var paths = {
        getAttendanceItemByCodes: "at/record/divergencetime/setting/AttendanceDivergenceName",
        getOptItemByAtr: "at/record/attendanceitem/daily/getattendcomparison/",
        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName",
        getMonthlyOptItemByAtr: "at/record/attendanceitem/monthly/getattendcomparison/",
        
        getAttendanceItemByAtr:         "at/record/businesstype/attendanceItem/getListByAttendanceAtr/",
        getMonthlyAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListMonthlyByAttendanceAtr/"
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