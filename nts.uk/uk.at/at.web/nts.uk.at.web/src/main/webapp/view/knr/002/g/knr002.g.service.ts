module knr002.g.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getTimeRecordReqSettings: "screen/at/tranfercontentsettings/getTimeRecordReqSettings",
        getWorkTypes: "screen/at/worktypetransfer/getWorkTypes",
        getWorkTimes: "screen/at/worktimetransfer/getWorkTimes",
        makeSelectedWorkTimes: "at/record/transferselecteddata/worktimes",
        makeSelectedWorkTypes: "at/record/transferselecteddata/worktypes",
        makeSelectedEmployees: "at/record/transferselecteddata/employees",
        makeSelectedBentoMenu: "at/record/transferselecteddata/bentomenu",
        confirm: "screen/at/checkremotesetting/confirm",
        determine: "screen/at/checkremotesetting/determine"
    };

    /**
    * Get Time Record Request Settings
    */
    export function getTimeRecordReqSettings(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getTimeRecordReqSettings+ "/" + empInfoTerCode);
    }
    /**
     * Get Worktype tobe sent
     */
    export function getWorkTypes(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getWorkTypes+ "/" + empInfoTerCode);
    }
    /**
     * Get Worktime tobe sent
     */
    export function getWorkTimes(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getWorkTimes+ "/" + empInfoTerCode);
    }
    /**
     * 
     */
    export function makeSelectedWorkTimes(command: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedWorkTimes, command);
    }
    /**
     * 
     */
    export function makeSelectedWorkTypes(command: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedWorkTypes, command);
    }
    /**
     * 
     */
    export function makeSelectedEmployees(command: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedEmployees, command);
    }
    /**
     * 
     */
    export function makeSelectedBentoMenu(command: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedBentoMenu, command);
    }
    /**
     * Confirm
     */
    export function confirm(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.confirm+ "/" + empInfoTerCode);
    }
    /**
     * determine
     */
    export function determine(empInfoTerCode: string,
                             sendEmployeeId: boolean,
                             employeeIds: Array<string>,
                             sendBentoMenu: boolean,
                             bentoMenuFrameNumbers: Array<string>,
                             sendWorkType: boolean,
                             workTypeCodes: Array<string>,
                             sendWorkTime: boolean,
                             workTimeCodes: Array<string>,
                             overTimeHoliday: boolean,
                             applicationReason: boolean,
                             stampReceive: boolean,
                             reservationReceive: boolean,
                             applicationReceive: boolean,
                             timeSetting: boolean,
                             remoteSetting: boolean,
                             reboot: boolean): JQueryPromise<any> {
        const dto = {
            empInfoTerCode: empInfoTerCode,
            sendEmployeeId: sendEmployeeId,
            employeeIds: employeeIds,
            sendBentoMenu: sendBentoMenu,
            bentoMenuFrameNumbers: bentoMenuFrameNumbers,
            sendWorkType: sendWorkType,
            workTypeCodes: workTypeCodes,
            sendWorkTime: sendWorkTime,
            workTimeCodes: workTimeCodes,
            overTimeHoliday: overTimeHoliday,
            applicationReason: applicationReason,
            stampReceive: stampReceive,
            reservationReceive: reservationReceive,
            applicationReceive: applicationReceive,
            timeSetting: timeSetting,
            remoteSetting: remoteSetting,
            reboot: reboot
        };
        return ajax(paths.determine, dto);
    }
}