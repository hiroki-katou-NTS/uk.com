module knr002.k.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getBentoMenu: "screen/at/bentomenutransfer/getbentomenu",
    };

    /**
    * get BentoMenu
    */
    export function getBentoMenu(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getBentoMenu+ "/" + empInfoTerCode);
    }
}