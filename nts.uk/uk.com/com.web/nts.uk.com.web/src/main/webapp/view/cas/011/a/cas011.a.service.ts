module nts.uk.com.view.cas011.a.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
            getLoginUserCompanyId:      "ctx/sys/auth/roleset/companyIdOfLoginUser",
            getAllRoleSets:             "ctx/sys/auth/roleset/findAllRoleSet",
            getRoleSetByRoleSetCd:      "ctx/sys/auth/roleset/findRoleSet",
            addRoleSet:                 "ctx/sys/auth/roleset/addRoleSet",
            updateRoleSet:              "ctx/sys/auth/roleset/updateRoleSet",
            removeRoleSet:              "ctx/sys/auth/roleset/deleteRoleSet",            
            getAllWebMenus:             "sys/portal/webmenu/find",
            getRoleById:                "ctx/sys/auth/roleset/",
    }

    //get all role set
    export function getAllRoleSets() : JQueryPromise<any>{
        return ajax(paths.getAllRoleSets);
    }

    //get role set
    export function getRoleSetByRoleSetCd(command) : JQueryPromise<any>{
        return ajax(paths.getRoleSetByRoleSetCd);
    }
    
    //insert
    export function addRoleSet(command) {
        return ajax(paths.addRoleSet, command);
    }
    
    //update
    export function updateRoleSet(command) {
        return ajax(paths.updateRoleSet, command);
    }
    //delete
    export function removeRoleSet(command) {
        return ajax(paths.removeRoleSet, command);
    }    
    
    //get all web menu
    export function getAllWebMenus() : JQueryPromise<any>{
        return ajax(paths.getAllWebMenus);
    }
    //get company id of login uset
    export function getLoginUserCompanyId() : JQueryPromise<any>{
        return ajax(paths.getLoginUserCompanyId);
    }
    
    //get company id of login uset
    export function getRoleById(comman) : JQueryPromise<any>{
        return ajax(paths.getRoleById, comman);
    }
}
