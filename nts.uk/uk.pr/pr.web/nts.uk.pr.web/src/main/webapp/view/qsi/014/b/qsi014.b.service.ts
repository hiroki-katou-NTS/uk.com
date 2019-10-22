module nts.uk.pr.view.qsi014.b.service {
    import ajax = nts.uk.request.ajax;
    let paths : any = {
        registerLossInfo : "ctx/pr/shared/lossinfo/registerLossInfo",
        start : "ctx/pr/report/printconfig/socinsurnoticreset/start/{0}"

    };
    export function start(empId: any): JQueryPromise<any>{
        let _path  =  nts.uk.text.format(paths.start, empId);
        return ajax("pr", _path);
    }
    export function registerLossInfo(empAddChangeInfoDto): JQueryPromise<any>{
        return ajax(paths.registerLossInfo, empAddChangeInfoDto);
    }



}
