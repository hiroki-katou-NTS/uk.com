module nts.uk.pr.view.qmm035.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        defaultData: "ctx/pr/core/socialinsurance/socialinsuranceoffice/start",
        findByCode: "ctx/pr/core/socialinsurance/socialinsuranceoffice/findByCode/{0}",
        update: "ctx/pr/core/socialinsurance/socialinsuranceoffice/update",
        create: "ctx/pr/core/socialinsurance/socialinsuranceoffice/create",
        deleteOffice: "ctx/pr/core/socialinsurance/socialinsuranceoffice/remove"
    }
    export function defaultData(): JQueryPromise<any> {
        return null;
       /* return ajax("pr",paths.defaultData);*/
    }
    export function findByCode(code : string) : JQueryPromise<any>{
        let _path = format(paths.findByCode, code);
        return null;
        /*return ajax('pr', _path);*/
    }
    export function update(command) : JQueryPromise<any>{
        return null;
        /*return ajax('pr', paths.update, command);*/
    }
    export function create(command) : JQueryPromise<any>{
        return null;
        /*return ajax('pr', paths.create, command);*/
    }
    export function deleteOffice(command: any){
        return null;
        /*return ajax('pr', paths.deleteOffice, command);*/
    }
}