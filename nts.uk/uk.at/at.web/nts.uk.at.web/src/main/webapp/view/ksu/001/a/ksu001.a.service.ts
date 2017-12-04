module ksu001.a.service {
    var paths: any = {
        getDataBasicSchedule: "screen/at/schedule/basicschedule/getDataBasicSchedule",
        getDataWorkScheduleState: "screen/at/schedule/basicschedule/getDataWorkScheduleState",
        registerData: "at/schedule/basicschedule/register",
        getShiftCondition: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftCondition",
        getShiftConditionCategory: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftConCategory",
        checkStateWorkTypeCode: "screen/at/schedule/basicschedule/checkStateWorkTypeCode",
        getDataWkpSpecificDate: "screen/at/schedule/basicschedule/getDataWkpSpecificDate",
        getDataComSpecificDate: "screen/at/schedule/basicschedule/getDataComSpecificDate",
        getDataPublicHoliday: "screen/at/schedule/basicschedule/getDataPublicHoliday",
        getDataWorkSchTimeZone: "screen/at/schedule/basicschedule/getDataWorkScheTimezone",
        getDataScheduleDisplayControl: "screen/at/schedule/basicschedule/getScheduleDisplayControl",
        getDataWorkEmpCombine: "screen/at/schedule/basicschedule/getWorkEmpCombine",
    }

    export function getDataBasicSchedule(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataBasicSchedule, obj);
    }

    export function registerData(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerData, obj);
    }
    export function getShiftCondition(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftCondition);
    }
    export function getShiftConditionCategory(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftConditionCategory);
    }

    export function getDataWorkScheduleState(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWorkScheduleState, obj);
    }

    export function checkStateWorkTypeCode(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkStateWorkTypeCode, obj);
    }

    export function getDataWkpSpecificDate(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWkpSpecificDate, obj);
    }

    export function getDataComSpecificDate(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataComSpecificDate, obj);
    }

    export function getDataPublicHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataPublicHoliday, obj);
    }

    export function getDataWorkSchTimeZone(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWorkSchTimeZone, obj);
    }

    export function getDataScheduleDisplayControl(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataScheduleDisplayControl);
    }

    export function getDataWorkEmpCombine(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWorkEmpCombine, obj);
    }
}