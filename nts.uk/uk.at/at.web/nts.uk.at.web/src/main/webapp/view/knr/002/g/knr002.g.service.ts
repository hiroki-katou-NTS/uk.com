module knr002.g.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getTimeRecordReqSettings: "screen/at/tranfercontentsettings/getTimeRecordReqSettings",
        getWorkTypes: "screen/at/worktypetransfer/getWorkTypes",
        getWorkTimes: "screen/at/worktimetransfer/getWorkTimes",
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
}