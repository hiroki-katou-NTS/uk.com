module nts.uk.at.view.kal003.b1.service {
    import req_ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
            getAttendComparison:            "at/record/attendanceitem/daily/getattendcomparison/{0}",
            getAttendCoutinousTime:         "at/record/attendanceitem/daily/getattendcoutinoustime",
            getAttendCoutinousWork:         "at/share/worktype/findNotDeprecated",
            getAttendCoutinousTimeZone:     "at/record/attendanceitem/daily/getattendcoutinoustimezone",
            getAttendCompound:              "at/record/attendanceitem/daily/getattendcompound/{0}",
            getAttendNameByIds:             "at/record/attendanceitem/daily/getattendnamebyids",
            getErrorAlarmCondition:         "at/record/attendanceitem/daily/geterroralarmcondition/{0}",
            getAttendanceItemByCodes:       "at/record/divergencetime/setting/AttendanceDivergenceName",
            findWorkTypeByCodes:            "at/share/worktype/findNotDeprecatedByListCode",
            getAttendanceItemByAtr:         "at/record/businesstype/attendanceItem/getListByAttendanceAtr/",

            getEnumSingleValueCompareTypse: "/at/function/alarm/checkcondition/kal003b/getEnumSingleValueCompareTypse",
            getEnumRangeCompareType:        "/at/function/alarm/checkcondition/kal003b/getEnumRangeCompareType",
            getEnumTypeCheckWorkRecord:     "/at/function/alarm/checkcondition/kal003b/getEnumTypeCheckWorkRecord",
            getEnumTargetSelectionRange:    "/at/function/alarm/checkcondition/kal003b/getEnumTargetSelectionRange",
            getEnumTargetServiceType:       "/at/function/alarm/checkcondition/kal003b/getEnumTargetServiceType",
            getEnumLogicalOperator:         "/at/function/alarm/checkcondition/kal003b/getEnumLogicalOperator",

    }

    ////アルゴリズム「日次の初期起動」を実行する
    //command: checkItem => return List<AttdItemDto>
    export function getDailyItemChkItemComparison(checkItem) : JQueryPromise<any> {
        return req_ajax(format(paths.getAttendComparison, checkItem));
    }

    //return List<AttdItemDto>
    export function getAttendCoutinousTime() : JQueryPromise<any> {
        return req_ajax(paths.getAttendCoutinousTime);
    }
    
    // return List<WorkTypeDto>
    export function getAttendCoutinousWork() : JQueryPromise<any> {
        return req_ajax(paths.getAttendCoutinousWork);
    }

    //return List<SimpleWorkTimeSettingDto>
    export function getAttendCoutinousTimeZone() : JQueryPromise<any> {
        return req_ajax(paths.getAttendCoutinousTimeZone);
    }
    
    //command erAlCheckId => return ???
    export function getAttendCompound(erAlCheckId) : JQueryPromise<any> {
        return req_ajax(format(paths.getAttendCompound, erAlCheckId));
    }

    //command List<Integer> dailyAttendanceItemIds => return List<DailyAttendanceItemNameAdapterDto>
    export function getAttendNameByIds(command) : JQueryPromise<any> {
        return req_ajax(paths.getAttendNameByIds, command);
    }
    
    // command erAlCheckId => return ErrorAlarmWorkRecordDto
    export function getErrorAlarmCondition(erAlCheckId) : JQueryPromise<any> {
        return req_ajax(format(paths.getErrorAlarmCondition, erAlCheckId));
    }

    //the same kdw007
    export function getAttendanceItemByCodes(codes) : JQueryPromise<any> {
        return req_ajax("at", paths.getAttendanceItemByCodes, codes);
    }
    
    export function getAttendanceItemByAtr(atr) : JQueryPromise<any>  {
        return nts.uk.request.ajax("at", paths.getAttendanceItemByAtr + atr);
    }
    /**
     * Find work type by list codes.
     */
    export function findWorkTypeByCodes(command: Array<string>): JQueryPromise<any> {
        return req_ajax(paths.findWorkTypeByCodes, command);
    }
    // get all Enums:
    export function getEnumSingleValueCompareTypse() : JQueryPromise<any> {
        return req_ajax(paths.getEnumSingleValueCompareTypse);
    }
    export function getEnumRangeCompareType() : JQueryPromise<any> {
        return req_ajax(paths.getEnumRangeCompareType);
    }
    export function getEnumTypeCheckWorkRecord() : JQueryPromise<any> {
        return req_ajax(paths.getEnumTypeCheckWorkRecord);
    }
    export function getEnumTargetSelectionRange() : JQueryPromise<any> {
        return req_ajax(paths.getEnumTargetSelectionRange);
    }
    export function getEnumTargetServiceType() : JQueryPromise<any> {
        return req_ajax(paths.getEnumTargetServiceType);
    }
    export function getEnumLogicalOperator() : JQueryPromise<any> {
        return req_ajax(paths.getEnumLogicalOperator);
    }
}
