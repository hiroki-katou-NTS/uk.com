module nts.uk.at.view.kdm001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
        getFurikyuMngDataExtraction: "at/record/remainnumber/paymana/getFurikyuMngDataExtraction/{0}/{1}/{2}/{3}",
        getNumberOfDayLeft: "at/record/remainnumber/paymana/getNumberOfDayLeft/{0}"
    }
    
    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }
    
    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }
    
    export function getFurikyuMngDataExtraction(empId: string, startDate: string, endDate: string, isPeriod: boolean) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getFurikyuMngDataExtraction, empId, startDate, endDate, isPeriod);
        return nts.uk.request.ajax("at", _path);
    }
}