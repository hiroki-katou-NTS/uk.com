module nts.uk.com.view.cas011.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;

    var paths = {
            getAllRoleSets:             "ctx/sys/auth/roleset/findAllRoleSet",
            getCurrentDefaultRoleSet:   "ctx/sys/auth/roleset/findDefaultRoleSet",
            addDefaultRoleSet:          "ctx/sys/auth/roleset/addRoleSet",
    }

    export function getAllRoleSet() {
        return ajax(path.getAllRoleSets);
    }

    export function getCurrentDefaultRoleSet() {
        return ajax(path.getCurrentDefaultRoleSet);
    }

    // add Default Role Set:
    export function addDefaultRoleSet(command) {
        return ajax(paths.addDefaultRoleSet, command);
    }
}

