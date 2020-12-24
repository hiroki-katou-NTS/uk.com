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
    export function makeSelectedWorkTimes(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedWorkTimes+ "/" + empInfoTerCode);
    }
    /**
     * 
     */
    export function makeSelectedWorkTypes(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedWorkTypes+ "/" + empInfoTerCode);
    }
    /**
     * 
     */
    export function makeSelectedEmployees(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedEmployees+ "/" + empInfoTerCode);
    }
    /**
     * 
     */
    export function makeSelectedBentoMenu(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.makeSelectedBentoMenu+ "/" + empInfoTerCode);
    }
}