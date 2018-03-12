module nts.uk.at.view.kmf022.m.service{
    var paths: any = {
        getAll: "at/request/application/setting/workplace/getall",
        update: "at/request/application/setting/workplace/update",
        remove: "at/request/application/setting/workplace/remove"
    }
    
    export function getAll(lstWkpId): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getAll, lstWkpId);
    }
    
    export function update(command): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.update, command);
    }
    
    export function remove(command): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.remove, command);
    }
}