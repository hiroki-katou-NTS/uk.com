module nts.uk.pr.view.qui001.c.service {
    import ajax = nts.uk.request.ajax;
    let paths : any = {
        start : "shared/empinsqualifiinfo/employmentinsqualifiinfo/empinsgetinfo/start/{0}",
        register : "shared/empinsqualifiinfo/employmentinsqualifiinfo/empinsgetinfo/register"
    };
    export function start(sid : any): JQueryPromise<any>{
        let _path = nts.uk.text.format(paths.start, sid);
        return ajax("pr",_path);
    }
    export function register(empInsGetInfoDto): JQueryPromise<any>{
        return ajax(paths.register, empInsGetInfoDto);
    }
}