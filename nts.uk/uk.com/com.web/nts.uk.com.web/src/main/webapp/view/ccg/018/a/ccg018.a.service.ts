module ccg018.a.service {
    var paths: any = {
        update: "sys/portal/toppagesetting/update",
        findDataOfTopPageJobSet: "sys/portal/toppagesetting/find",
        findByCId: "sys/portal/toppagesetting/findByCId",
        findByAfterLoginDisplay: "sys/portal/standardmenu/findByAfterLoginDisplay",
        findByAfterLgDisSysMenuCls: "sys/portal/standardmenu/findByAfterLgDisSysMenuCls",
    }
    
    export function findDataOfTopPageJobSet(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findDataOfTopPageJobSet); 
    }

    export function findByCId(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findByCId); 
    }

    export function update(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.update, command);
    }

    export function findByAfterLoginDisplay(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findByAfterLoginDisplay);
    }

    export function findByAfterLgDisSysMenuCls(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findByAfterLgDisSysMenuCls);
    }
}