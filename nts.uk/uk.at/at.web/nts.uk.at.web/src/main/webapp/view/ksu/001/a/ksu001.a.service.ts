module nts.uk.at.view.ksu001.a.service {
    let paths: any = {
        getDataBasicSchedule: "screen/at/schedule/basicschedule/getBasicScheduleWithJDBC",
        getDataWorkScheduleState: "screen/at/schedule/basicschedule/getDataWorkScheduleState",
        registerData: "at/schedule/basicschedule/register",
        getShiftCondition: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftCondition",
        getShiftConditionCategory: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftConCategory",
        buildTreeShiftCondition: "at/schedule/shift/shiftCondition/shiftCondition/buildTreeShiftCondition",
        getDataScheduleDisplayControl: "screen/at/schedule/basicschedule/getScheduleDisplayControl",
        getDataWorkEmpCombine: "screen/at/schedule/basicschedule/getWorkEmpCombine",
        getDataSpecDateAndHoliday: "screen/at/schedule/basicschedule/getDataSpecDateAndHoliday",
        findWorkPlaceById: "bs/employee/workplace/info/findDetail",
        getDataComPattern: "at/schedule/shift/management/getListShijtPalletsByCom",
        getDataWkpPattern: "at/schedule/shift/management/shiftpalletorg/getbyWorkplaceId",
        
        // lai start
        getSendingPeriod: "screen/at/schedule/schedule/start/getSendingPeriod"
    }
    
    export function getSendingPeriod(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getSendingPeriod, obj);
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
    
    export function buildTreeShiftCondition(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.buildTreeShiftCondition);
    }

    export function getDataWorkScheduleState(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWorkScheduleState, obj);
    }

    export function getDataScheduleDisplayControl(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataScheduleDisplayControl);
    }

    export function getDataWorkEmpCombine(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWorkEmpCombine, obj);
    }

    export function getDataSpecDateAndHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataSpecDateAndHoliday, obj);
    }

    export function getDataComPattern(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataComPattern);
    }

    export function getDataWkpPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWkpPattern, obj);
    }
    export function getWorkPlaceById(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findWorkPlaceById, data);
    }
}