module nts.uk.pr.view.qmm035.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        defaultData: "pr/core/printdata/start",
        findByCode: "pr/core/printdata/getbycode/{0}",
        update: "ctx/pr/core/socialinsurance/socialinsuranceoffice/update",
        create: "pr/core/printdata/create",
        delete: "pr/core/printdata/delete"
    }
    export function defaultData(): JQueryPromise<any> {
       return ajax("pr",paths.defaultData);
    }
    export function findByCode(code : string) : JQueryPromise<any>{
        let _path = format(paths.findByCode, code);
        return ajax('pr', _path);
    }
    export function update(command) : JQueryPromise<any>{
        return null;
        /*return ajax('pr', paths.update, command);*/
    }
    export function create(command) : JQueryPromise<any>{
        return ajax('pr', paths.create, command);
    }
    export function deleteCompany(command: any){
       return ajax('pr', paths.delete, command);
    }
}