module ksu001.a.service {
    var paths: any = {
        getDataBasicSchedule: "screen/at/schedule/basicschedule/getData",
        getDataWorkScheduleState: "at/schedule/workschedulestate/findAll",
        registerData: "at/schedule/basicschedule/register",
        getShiftCondition: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftCondition",
        getShiftConditionCategory: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftConCategory"
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
    
    export function getDataWorkScheduleState(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWorkScheduleState);
    }
}