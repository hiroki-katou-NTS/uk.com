module knr002.h.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getEmployees: "screen/at/employeestransfer/getEmployees",
    };
    /**
     * 
     */
    export function getEmployees(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getEmployees+ "/" + empInfoTerCode);
    }
}

  