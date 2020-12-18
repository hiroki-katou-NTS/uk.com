module knr002.k.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getBentoMenu: "screen/at/bentomenutransfer/getBentoMenu",
    };

    /**
    * Get Destination Copy List
    * 
    * 
    */
   export function getBentoMenu(empInfoTerCode: any): JQueryPromise<any> {
    return ajax(paths.getBentoMenu+ "/" + empInfoTerCode);
}
}