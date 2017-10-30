module nts.uk.at.view.kaf004.e.service {
   var paths: any = {
        getByCode: "at/request/lateorleaveearly/findbycode",
        createLateOrLeaveEarly: "at/request/lateorleaveearly/create",
        deleteLateOrLeaveEarly: "at/request/lateorleaveearly/delete",
        updateLateOrLeaveEarly: "at/request/lateorleaveearly/update",
    }

    /** Get TitleMenu */
    export function getByCode(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getByCode);
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