module nts.uk.at.view.kaf002.shr {
    import ajax = nts.uk.request.ajax;
    export module service {
        var paths: any = {
            findByAppID: "at/request/application/stamp/findByAppID",
            insert: "at/request/application/stamp/insert",
            update: "at/request/application/stamp/update"
        }
        
        export function findByAppID(): JQueryPromise<any> {
            return ajax(paths.findByAppID);
        }
        
        export function insert(command): JQueryPromise<any> {
            return ajax(paths.insert, command);
        }
        
        export function update(command): JQueryPromise<any> {
            return ajax(paths.update, command);
        }
    }
}