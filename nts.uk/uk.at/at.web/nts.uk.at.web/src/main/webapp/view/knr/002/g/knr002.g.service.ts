module knr002.g.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getTimeRecordReqSettings: "screen/at/tranfercontentsettings/gettimerecordreqsettings",
    };

    /**
    * Get Time Record Request Settings
    */
   export function getTimeRecordReqSettings(empInfoTerCode: any): JQueryPromise<any> {
    return ajax(paths.getTimeRecordReqSettings+ "/" + empInfoTerCode);
    }
}