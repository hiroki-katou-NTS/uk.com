module ccg018.a.service {
    var paths: any = {
        update: "sys/portal/toppagesetting/update",
        findByCId: "sys/portal/toppagesetting/findByCId"
    }

    export function findByCId(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findByCId);
    }

    export function update(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.update, command);
    }
}