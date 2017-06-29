module ccg018.a.service {
    var paths: any = {
        update: "sys/portal/toppagesetting/jobset/updateTopPageJobSet",
        findDataOfTopPageJobSet: "sys/portal/toppagesetting/jobset/find",
        findByCId: "sys/portal/toppagesetting/findByCId",
        findBySystemMenuCls: "sys/portal/standardmenu/findBySystemMenuCls",
        findDataOfJobTitle: "basic/company/organization/jobtitle/findall"
    }

    export function findDataOfTopPageJobSet(listJobId): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findDataOfTopPageJobSet, listJobId);
    }

    export function findByCId(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findByCId);
    }

    export function update(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.update, command);
    }

    export function findBySystemMenuCls(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findBySystemMenuCls);
    }

    export function findDataOfJobTitle(baseDate: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findDataOfJobTitle, { baseDate: baseDate });
    }
}