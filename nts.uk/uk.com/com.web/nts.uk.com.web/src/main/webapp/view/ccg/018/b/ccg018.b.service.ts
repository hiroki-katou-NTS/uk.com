module ccg018.b.service {
    var paths: any = {
        findBySystemMenuCls: "sys/portal/standardmenu/findBySystemMenuCls",
        findTopPagePersonSet: "sys/portal/toppagesetting/personset/findBySid",
        update: "sys/portal/toppagesetting/personset/update",
        remove: "sys/portal/toppagesetting/personset/remove"
    }

    export function findBySystemMenuCls(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findBySystemMenuCls);
    }

    export function findTopPagePersonSet(listSid: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findTopPagePersonSet, listSid);
    }

    export function update(obj: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.update, obj);
    }

    export function remove(obj: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.remove, obj);
    }
}