module knr002.f.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getRecoveryTargeTertList: "screen/at/recoverytargetter/getAll",
    };

    /**
    * Get Destination Copy List
    * 
    * 
    */
   export function getRecoveryTargeTertList(modelEmpInfoTer: number): JQueryPromise<any> {
    return ajax(paths.getRecoveryTargeTertList+ "/" + modelEmpInfoTer);
}
}