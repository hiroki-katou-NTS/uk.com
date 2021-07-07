module knr002.f.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getRecoveryTargetList: "screen/at/recoverytargetter/getAll",
        recovery: "screen/at/performrecovery/recovery",
        updateRemoteSettings: 'screen/knr002/c/updateRemoteSetting'
    };

    export function updateRemoteSettings(input: any): JQueryPromise<any> {
        return ajax(paths.updateRemoteSettings, input);
    }

    /**
     * Get Recovery Target List
     * 
     */
    export function getRecoveryTargetList(modelEmpInfoTer: number): JQueryPromise<any> {
        return ajax(paths.getRecoveryTargetList+ "/" + modelEmpInfoTer);
    }

    /**
     * 
     */
    export function recovery(empInfoTerCode: string, terminalCodeList: Array<any>): JQueryPromise<any> {
        const dto = {
            empInfoTerCode: empInfoTerCode,
            terminalCodeList: terminalCodeList
        };
        return ajax(paths.recovery, dto);
    }
}