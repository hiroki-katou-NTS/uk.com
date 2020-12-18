module knr002.d.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getDestinationCopyList: "screen/at/destinationcopy/getDestinationCopyList",
    };

    /**
    * Get Destination Copy List
    * 
    */
   export function getDestinationCopyList(empInfoTerCode: any): JQueryPromise<any> {
    return ajax(paths.getDestinationCopyList+ "/" + empInfoTerCode);
}
}