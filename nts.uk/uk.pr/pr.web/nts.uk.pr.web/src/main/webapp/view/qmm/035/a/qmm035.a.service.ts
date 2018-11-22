module nts.uk.pr.view.qmm035.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        defaultData: "pr/core/fromsetting/start",
        findByCode: "pr/core/fromsetting/getbycode/{0}",
        update: "pr/core/fromsetting/update",
        create: "pr/core/fromsetting/create",
        delete: "pr/core/fromsetting/delete"
    }
    export function defaultData(): JQueryPromise<any> {
       return ajax("pr",paths.defaultData);
    }
    export function findByCode(code : string) : JQueryPromise<any>{
        let _path = format(paths.findByCode, code);
        return ajax('pr', _path);
    }
    export function update(command) : JQueryPromise<any>{
        return ajax('pr', paths.update, command);
    }
    export function create(command) : JQueryPromise<any>{
        return ajax('pr', paths.create, command);
    }
    export function deleteCompany(command: any){
       return ajax('pr', paths.delete, command);
    }
}