module nts.uk.pr.view.qmm005.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        findDisplayRegister: "ctx.pr.core.ws.wageprovision.processdatecls/findDisplayRegister",
        registerProcessing :"nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls/registerProcessing"
    };

    export function findDisplayRegister(): JQueryPromise<any> {
        return ajax('pr', paths.findDisplayRegister);
    }
    
    export function registerProcessing(): JQueryPromise<any> {
        let _path = format(paths.registerProcessing);
        return ajax('com', _path);
    }



}