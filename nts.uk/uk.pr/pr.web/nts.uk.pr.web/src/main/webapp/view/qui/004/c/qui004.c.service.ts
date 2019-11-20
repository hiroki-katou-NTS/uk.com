module nts.uk.pr.view.qui004.c.service {
    import ajax = nts.uk.request.ajax;
    let paths : any = {
        start : "shared/empinsqualifiinfo/employmentinsqualifiinfo/start/{0}",
        register : "shared/empinsqualifiinfo/employmentinsqualifiinfo/register"
    };
    export function start(sid : any): JQueryPromise<any>{
        let _path = nts.uk.text.format(paths.start, sid);
        return ajax("pr",_path);
    }
    export function register(empInsLossInfoDto): JQueryPromise<any>{
        return ajax(paths.register, empInsLossInfoDto);
    }
}