module nts.uk.at.view.kal003.c.service {
    var paths = {
        getAttendanceItemByCodes: "at/record/divergencetime/AttendanceDivergenceName",
        getOptItemByAtr: "at/record/attendanceitem/daily/getattendcomparison/",
        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/getMonthlyAttendanceDivergenceName",
        getListMonthlyByAtrPrimitive: "at/record/businesstype/attendanceItem/getListMonthlyByAtrPrimitive/",
        getMonthlyOptItemByAtr: "at/record/attendanceitem/monthly/getattendcomparison/",
        //get name monhtly
        getNameMonthly  :"screen/at/correctionofdailyperformance/getNameMonthlyAttItem",
        
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
            //return nts.uk.request.ajax("at", paths.getListMonthlyByAtrPrimitive + atr);
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
    export function getNameMonthly(listID : any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getNameMonthly,listID);
        }
}