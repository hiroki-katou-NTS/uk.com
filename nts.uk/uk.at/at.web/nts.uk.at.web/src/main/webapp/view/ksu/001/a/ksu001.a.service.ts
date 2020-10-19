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
        getSendingPeriod: "screen/at/schedule/schedule/start/getSendingPeriod",
        
        getDataStartScreen: "screen/at/schedule/start",
        getDataOfShiftMode: "screen/at/schedule/shift",
        getDataOfShortNameMode: "screen/at/schedule/shortname",
        getDataOfTimeMode: "screen/at/schedule/time",
        getDataChangeMonth: "screen/at/schedule/change-month",
        getDataWhenChangeModePeriod: "screen/at/schedule/change-mode-period",
        orderEmployee: "screen/at/schedule/order-employee",
        validWhenPaste: "screen/at/schedule/valid-when-paste",
        changeWokPlace: "screen/at/schedule/change-workplace"
    }
    
    export function getDataStartScreen(param): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataStartScreen, param);
    }

    export function getDataOfShiftMode(param): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataOfShiftMode, param);
    }

    export function getDataOfShortNameMode(param): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataOfShortNameMode, param);
    }

    export function getDataOfTimeMode(param): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataOfTimeMode, param);
    }

    export function getSendingPeriod(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getSendingPeriod, obj);
    }

    export function getDataChangeMonth(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataChangeMonth, obj);
    }
    
       export function getDataWhenChangeModePeriod(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWhenChangeModePeriod, obj);
    }

    export function getListEmpIdSorted(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.orderEmployee, obj);
    }
    
    export function validWhenPaste(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.validWhenPaste, obj);
    }

    export function changeWokPlace(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.changeWokPlace, obj);
    }
    
    
}