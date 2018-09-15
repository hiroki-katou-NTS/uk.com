module nts.uk.pr.view.qmm005.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        findDisplayRegister: "nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls/findDisplayRegister/{0}",       
        registerProcessing :"nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls/registerProcessing"
    };

    export function findDisplayRegister(processingCategoryNo: number): JQueryPromise<any> {
        let _path = format(paths.findDisplayRegister, processingCategoryNo);
        return ajax('com', _path);
    }
    
    export function registerProcessing(): JQueryPromise<any> {
        let _path = format(paths.registerProcessing);
        return ajax('com', _path);
    }

    export function getSetDaySupports(): JQueryPromise<any> {
        return ajax(paths.getSetDaySupports);
    }

}