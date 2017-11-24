module nts.uk.com.view.cas011.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
            getAllRoleSet:             "ctx/sys/auth/roleset/findAllRoleSet",
            getCurrentDefaultRoleSet:   "ctx/sys/auth/roleset/findDefaultRoleSet",
            addDefaultRoleSet:          "ctx/sys/auth/roleset/addRoleSet"
    }

    export function getAllRoleSet() : JQueryPromise<any> {
        return ajax(path.getAllRoleSet);
    }

    export function getCurrentDefaultRoleSet() : JQueryPromise<any> {
        return ajax(path.getCurrentDefaultRoleSet);
    }

    // add Default Role Set:
    export function addDefaultRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.addDefaultRoleSet, command);
    }
}

