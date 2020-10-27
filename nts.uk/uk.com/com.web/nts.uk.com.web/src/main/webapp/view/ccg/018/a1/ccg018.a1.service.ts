module ccg018.a1.service {
    var paths: any = {
        update: "sys/portal/toppagesetting/roleset/save",
        // update: "sys/portal/toppagesetting/jobset/updateTopPageJobSet",
        findDataOfTopPageJobSet: "sys/portal/toppagesetting/jobset/find",
        findDataOfJobTitle: "bs/employee/jobtitle/findAll",
        findAllRoleSet: "ctx/sys/auth/roleset/findallroleset",
        findAllTopPageRoleSet: "sys/portal/toppagesetting/roleset/findAll"
    }

    export function findDataOfTopPageJobSet(listJobId): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findDataOfTopPageJobSet, listJobId);
    }

    export function update(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.update, command);
    }

    export function findDataOfJobTitle(baseDate: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findDataOfJobTitle, { baseDate: baseDate });
    }

    export function findAllRoleSet(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findAllRoleSet);
    }

    export function findAllTopPageRoleSet(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findAllTopPageRoleSet);
    }
}