module ccg018.a1.service {
    var paths: any = {
        update: "sys/portal/toppagesetting/jobset/updateTopPageJobSet",
        findDataOfTopPageJobSet: "sys/portal/toppagesetting/jobset/find",
        findDataOfJobTitle: "basic/company/organization/jobtitle/findall"
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
}