module nts.uk.at.view.kaf004.b.service {
   var paths: any = {
        getByCode: "at/request/lateorleaveearly/findbycode",
        createLateOrLeaveEarly: "at/request/lateorleaveearly/create",
        deleteLateOrLeaveEarly: "at/request/lateorleaveearly/delete",
        updateLateOrLeaveEarly: "at/request/lateorleaveearly/update",
    }

    /** Get TitleMenu */
    export function getByCode(): JQueryPromise<Array> {
        return nts.uk.request.ajax("com", paths.getByCode);
    }
      export function createLateOrLeaveEarly(): JQueryPromise<Array> {
        return nts.uk.request.ajax("com", paths.createLateOrLeaveEarly);
    }
     export function deleteLateOrLeaveEarly(): JQueryPromise<Array> {
        return nts.uk.request.ajax("com", paths.deleteLateOrLeaveEarly);
    }
     export function updateLateOrLeaveEarly(): JQueryPromise<Array> {
        return nts.uk.request.ajax("com", paths.updateLateOrLeaveEarly);
    }
}