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
                             sendBentoMenu: boolean,
                             sendWorkType: boolean,
                             sendWorkTime: boolean,
                             overTimeHoliday: boolean,
                             applicationReason: boolean,
                             stampReceive: boolean,
                             reservationReceive: boolean,
                             applicationReceive: boolean,
                             timeSetting: boolean,
                             reboot: boolean): JQueryPromise<any> {
        const dto = {
            empInfoTerCode: empInfoTerCode,
            sendEmployeeId: sendEmployeeId,
            sendBentoMenu: sendBentoMenu,
            sendWorkType: sendWorkType,
            sendWorkTime: sendWorkTime,
            overTimeHoliday: overTimeHoliday,
            applicationReason: applicationReason,
            stampReceive: stampReceive,
            reservationReceive: reservationReceive,
            applicationReceive: applicationReceive,
            timeSetting: timeSetting,
            reboot: reboot
        };
        return ajax(paths.determine, dto);
    }
}