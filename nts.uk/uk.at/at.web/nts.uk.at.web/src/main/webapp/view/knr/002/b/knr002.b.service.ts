module knr002.b.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getInPeriod: "screen/at/monitoringempinfoterminal/getInPeriod",
    };

    /**
     * Get Log in the Period
    */
    export function getInPeriod(empInfoTerCode: string, sTime: string, eTime: string): JQueryPromise<any> {
        const dto = {
            empInfoTerCode: empInfoTerCode,
            preTimeSuccDate: sTime,
            lastestTimeSuccDate: eTime
        };
        return ajax(paths.getInPeriod, dto);
    }
}