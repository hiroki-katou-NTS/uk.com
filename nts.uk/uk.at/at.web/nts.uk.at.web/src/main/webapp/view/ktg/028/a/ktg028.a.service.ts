module nts.uk.at.view.ktg028.a.service {
    var paths: any = {
        findAll: "sys/portal/widget/findAll",
        add: "sys/portal/widget/add",
        update: "sys/portal/widget/update",
        remove: "sys/portal/widget/delete"
    }
    export function add(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.add, obj);
    }
    export function update(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.update, obj);
    }
    export function remove(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.remove, obj);
    }
    export function findAll(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findAll);
    }
}