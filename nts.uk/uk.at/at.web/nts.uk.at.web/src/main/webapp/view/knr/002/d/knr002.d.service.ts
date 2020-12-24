module knr002.d.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getDestinationCopyList: "screen/at/destinationcopy/getDestinationCopyList",
        checkRemoteSettingsToCopy: "screen/at/checkremotesetting/check",
        registerAndSubmitChanges: "at/record/knr002/cmd/c/registerAndSubmit",
    };

    /**
    * Get Destination Copy List
    * 
    */
    export function getDestinationCopyList(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getDestinationCopyList+ "/" + empInfoTerCode);
    }
    /**
     * check remote setting to copy
     * 
     */
    export function checkRemoteSettingsToCopy(listEmpCode: Array<string>): JQueryPromise<any> {
        const dto = {
            listEmpCode : listEmpCode
        };
        return ajax(paths.checkRemoteSettingsToCopy, dto);
    }
    /**
     * Register and submit changes
     */
   export function registerAndSubmitChanges(data: any): JQueryPromise<any> {
    return ajax(paths.registerAndSubmitChanges, data);
}
}