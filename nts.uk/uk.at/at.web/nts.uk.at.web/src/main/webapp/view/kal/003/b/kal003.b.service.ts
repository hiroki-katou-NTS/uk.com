module nts.uk.at.view.kal003.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
            //アルゴリズム「日次の初期起動」を実行する
            
            getAllDailyAttendanceItemBy:        "at/function/alarm/checkcondition/kal003b/getAllDailyAttendanceItemBy",
            getOptionItemByAtr:                 "at/function/alarm/checkcondition/kal003b/findByAtr",
            getAllAttendanceAndFrameLinking:    "at/function/alarm/checkcondition/kal003b/getAllAttendanceAndFrameLinking",
            getDailyAttendanceItemByChkItem:    "/at/function/alarm/checkcondition/kal003b/getDailyAttendanceItemByChkItem",
            getEnumSingleValueCompareTypse:     "/at/function/alarm/checkcondition/kal003b/getEnumSingleValueCompareTypse",
            getEnumRangeCompareType:            "/at/function/alarm/checkcondition/kal003b/getEnumRangeCompareType",
            getEnumTypeCheckWorkRecord:         "/at/function/alarm/checkcondition/kal003b/getEnumTypeCheckWorkRecord",
            getEnumTargetSelectionRange:        "/at/function/alarm/checkcondition/kal003b/getEnumTargetSelectionRange",
            getEnumTargetServiceType:           "/at/function/alarm/checkcondition/kal003b/getEnumTargetServiceType",
            getEnumLogicalOperator:             "/at/function/alarm/checkcondition/kal003b/getEnumLogicalOperator",

    }
    ////アルゴリズム「日次の初期起動」を実行する

    export function getAllDailyAttendanceItemBy(command) : JQueryPromise<any> {
        return ajax(paths.getAllDailyAttendanceItemBy, command);
    }

    export function getCurrentDefaultRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.getOptionItemByAtr, command);
    }

    export function getDailyAttendanceItemByChkItem(command) : JQueryPromise<any> {
        return ajax(paths.getDailyAttendanceItemByChkItem, command);
    }
    // get all Enums:
    export function getEnumSingleValueCompareTypse() : JQueryPromise<any> {
        return ajax(paths.getEnumSingleValueCompareTypse);
    }
    export function getEnumRangeCompareType() : JQueryPromise<any> {
        return ajax(paths.getEnumRangeCompareType);
    }
    export function getEnumTypeCheckWorkRecord() : JQueryPromise<any> {
        return ajax(paths.getEnumTypeCheckWorkRecord);
    }
    export function getEnumTargetSelectionRange() : JQueryPromise<any> {
        return ajax(paths.getEnumTargetSelectionRange);
    }
    export function getEnumTargetServiceType() : JQueryPromise<any> {
        return ajax(paths.getEnumTargetServiceType);
    }
    export function getEnumLogicalOperator() : JQueryPromise<any> {
        return ajax(paths.getEnumLogicalOperator);
    }
}

