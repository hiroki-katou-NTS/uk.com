module nts.uk.pr.view.qsi013.b.service {
    import ajax = nts.uk.request.ajax;

    let paths : any = {
        registerLossInfo : "ctx/pr/shared/lossinfo/registerLossInfo",
        getLossInfoById : "ctx/pr/shared/lossinfo/getLossInfo/{0}"

    };

    export function registerLossInfo(lossInfoCommand): JQueryPromise<any>{
        return ajax(paths.registerLossInfo, lossInfoCommand);
    }

    export function getLossInfoById(empId: any): JQueryPromise<any>{
        let _path  =  nts.uk.text.format(paths.getLossInfoById, empId);
        return ajax("pr", _path);
    }
}