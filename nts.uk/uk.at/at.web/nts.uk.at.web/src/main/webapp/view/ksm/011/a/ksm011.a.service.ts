module nts.uk.at.view.ksm011.a.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findScheFuncControl: "ctx/at/schedule/setting/functioncontrol/findScheFuncControl"
    }
    
    /**
     *  Find all data
     */
    export function findScheFuncControl(): JQueryPromise<ScheFuncControlDto> {
        return nts.uk.request.ajax("at", paths.findScheFuncControl);
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
