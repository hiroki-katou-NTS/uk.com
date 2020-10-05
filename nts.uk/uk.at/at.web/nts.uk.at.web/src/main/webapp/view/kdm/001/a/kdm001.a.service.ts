module nts.uk.at.view.kdm001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWkp",
        getFurikyuMngDataExtraction: "at/record/remainnumber/paymana/getFurikyuMngDataExtraction/{0}/{1}",
        /* A4_2_9 削除 */
        removePayout: "at/record/remainnumber/paymana/deletePaymentManagementData"
    }
    
    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }
    
    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }
    
    export function getFurikyuMngDataExtraction(empId: string, isPeriod: boolean) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getFurikyuMngDataExtraction, empId, isPeriod);
        return nts.uk.request.ajax("at", _path);
    }

    /* A4_2_9 削除 */
    export function removePayout(command: any): JQueryPromise<any>{
        return ajax(paths.removePayout, command);
    }
}