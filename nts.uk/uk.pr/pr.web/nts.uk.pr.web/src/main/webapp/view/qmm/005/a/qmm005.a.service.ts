module nts.uk.pr.view.qmm005.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        findDisplayRegister: "ctx.pr.core.ws.wageprovision.processdatecls/findDisplayRegister",
        registerProcessing :"ctx.pr.core.ws.wageprovision.processdatecls/registerProcessing"
    };

    export function findDisplayRegister(): JQueryPromise<any> {
        return ajax('pr', paths.findDisplayRegister);
    }
    
    export function registerProcessing(command): JQueryPromise<any> {
        return ajax('pr', paths.registerProcessing, command);
    }



}