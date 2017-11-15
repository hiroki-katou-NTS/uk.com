module nts.uk.at.view.kaf005.shr.service {
    var paths: any = {
        getOvertimeByUI: "at/request/application/overtime/getOvertimeByUI",
        createOvertime: "at/request/application/overtime/create",
        deleteOvertime: "at/request/application/overtime/delete",
        updateOvertime: "at/request/application/overtime/update",
    }

    /** Get TitleMenu */
    export function getOvertimeByUI(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getOvertimeByUI, param);
    }
      export function createOvertime(overtime: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.createOvertime,overtime);
    }
     export function deleteOvertime(appID : string): JQueryPromise<void> { 
        return nts.uk.request.ajax("at", paths.deleteOvertime,appID);
    }
     export function updateOvertime(overtime:any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.updateOvertime ,overtime);
    }
}