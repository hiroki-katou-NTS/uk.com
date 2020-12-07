module knr002.f.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getDestinationCopyList: "screen/at/destinationcopy/getdestinationcopylist",
    };

    /**
    * Get Destination Copy List
    * 
    * 
    */
   export function getDestinationCopyList(empInfoTerCode: any): JQueryPromise<any> {
    return ajax(paths.getDestinationCopyList+ "/" + empInfoTerCode);
}
}