module nts.uk.at.view.kmf022.m.service {
    import ajax = nts.uk.request.ajax;

    var paths: any = {
        getAll: "at/request/application/setting/workplace/getall",
        update: "at/request/application/setting/workplace/update",
        remove: "at/request/application/setting/workplace/remove",
        getComConfig: "at/request/application/setting/workplace/get-com",
        saveComConfig: "at/request/application/setting/workplace/save-com-config"
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

    export function getCom() { return ajax(paths.getComConfig) };
    export function saveCom(command) { return ajax(paths.saveComConfig, command) };
}