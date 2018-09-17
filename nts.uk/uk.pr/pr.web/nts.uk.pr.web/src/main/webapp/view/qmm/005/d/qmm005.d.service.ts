module nts.uk.pr.view.qmm005.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {

        registerprocessingsegment :"ctx/pr/core/ws/wageprovision/registerprocessingsegment",
        updateprocessingsegment :"ctx/pr/core/ws/wageprovision/updateRegistedProcessing",
        findprocessingsegment:"ctx.pr.core.ws.wageprovision.processdatecls/findfindRegistedProcessing/{0}",
        deleteprocessingsegment:"ctx.pr.core.ws.wageprovision.processdatecls/deleteRegistedProcessing/{0}"
    };

    export function findDisplayRegister(processCateNo: number): JQueryPromise<any> {
        let _path = format(paths.findprocessingsegment, processCateNo);
        return ajax('pr', _path);
    }

    export function deleteDisplayRegister(processCateNo: number): JQueryPromise<any> {
        let _path = format(paths.deleteprocessingsegment, processCateNo);
        return ajax('pr', _path);
    }

    export function registerprocessingsegment(command): JQueryPromise<any> {
        return ajax('pr', paths.registerprocessingsegment, command);
    }
    
    export function updateprocessingsegment(command): JQueryPromise<any> {
        return ajax('pr', paths.updateprocessingsegment, command);
    }


}