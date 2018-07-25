module nts.uk.at.view.ksm011.a.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findScheFuncControl: "ctx/at/schedule/setting/functioncontrol/findScheFuncControl",
        saveScheFuncControl: "ctx/at/schedule/setting/functioncontrol/saveScheFuncControl",
        getShiftConditionCat: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftConCategory",
        getShiftCondition: "at/schedule/shift/shiftCondition/shiftCondition/getAllShiftCondition",
        checkExistedItems: "at/schedule/shift/shiftCondition/shiftCondition/checkExistedItems"
    }
    
    /**
     *  Get Schedule Function Control
     */
    export function checkExistedItems(items): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkExistedItems,items);
    }
    
    /**
     *  Get Schedule Function Control
     */
    export function findScheFuncControl(): JQueryPromise<ScheFuncControlDto> {
        return nts.uk.request.ajax("at", paths.findScheFuncControl);
    }
    
    /**
     *  Save Schedule Function Control
     */
    export function saveScheFuncControl(data: ScheFuncControlDto): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveScheFuncControl, data);
    } 
    
    /**
     *  Get Shift Condition Category
     */
    export function getShiftConditionCat(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftConditionCat);
    }
    
    /**
     *  Get Shift Condition
     */
    export function getShiftCondition(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftCondition);
    }
    
    export interface ScheFuncControlDto {
        alarmCheckUseCls: number,
        confirmedCls: number,
        publicCls: number,
        outputCls: number,
        workDormitionCls: number,
        teamCls: number,
        rankCls: number,
        startDateInWeek: number,
        shortNameDisp: number,
        timeDisp: number,
        symbolDisp: number,
        twentyEightDaysCycle: number,
        lastDayDisp: number,
        individualDisp: number,
        dispByDate: number,
        indicationByShift: number,
        regularWork: number,
        fluidWork: number,
        workingForFlex: number,
        overtimeWork: number,
        normalCreation: number,
        simulationCls: number,
        captureUsageCls: number,
        completedFuncCls: number,
        howToComplete: number,
        alarmCheckCls: number,
        executionMethod: number,
        handleRepairAtr: number,
        confirm: number,
        searchMethod: number,
        searchMethodDispCls: number,
        scheFuncCond: Array<ScheFuncCondDto>   
    }
    
    export interface ScheFuncCondDto {
        conditionNo: number,
    }
}
