module nts.uk.at.view.kaf005.shr.service {
    var paths: any = {
        getOvertimeByUI: "at/request/application/overtime/getOvertimeByUI",
        createLateOrLeaveEarly: "at/request/lateorleaveearly/create",
        deleteLateOrLeaveEarly: "at/request/lateorleaveearly/delete",
        updateLateOrLeaveEarly: "at/request/lateorleaveearly/update",
    }

    /** Get TitleMenu */
    export function getOvertimeByUI(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getOvertimeByUI, param);
    }
      export function createLateOrLeaveEarly(lateOrLeaveEarly: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.createLateOrLeaveEarly,lateOrLeaveEarly);
    }
     export function deleteLateOrLeaveEarly(appID : string): JQueryPromise<void> { 
        return nts.uk.request.ajax("at", paths.deleteLateOrLeaveEarly,appID);
    }
     export function updateLateOrLeaveEarly(lateOrLeaveEarly:any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.updateLateOrLeaveEarly ,lateOrLeaveEarly);
    }
}