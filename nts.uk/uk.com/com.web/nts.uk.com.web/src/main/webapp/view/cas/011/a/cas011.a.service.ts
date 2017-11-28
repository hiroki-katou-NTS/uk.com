module nts.uk.com.view.cas011.a.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
            getCompanyIdOfLoginUser:    "ctx/sys/auth/roleset/companyIdOfLoginUser",
            getAllRoleSet:             "ctx/sys/auth/roleset/findAllRoleSet",
            getRoleSetByRoleSetCd:      "ctx/sys/auth/roleset/findRoleSet/{roleSetCd}",
            addRoleSet:                 "ctx/sys/auth/roleset/addRoleSet",
            updateRoleSet:              "ctx/sys/auth/roleset/updateRoleSet",
            removeRoleSet:              "ctx/sys/auth/roleset/deleteRoleSet",            
            getAllWebMenu:             "sys/portal/webmenu/find",
            getRoleById:                "ctx/sys/auth/roleset/"
    }

    //get all role set
    export function getAllRoleSet() : JQueryPromise<any>{
        return ajax(paths.getAllRoleSet);
    }

    //get role set
    export function getRoleSetByRoleSetCd(command) : JQueryPromise<any>{
        return ajax(paths.getRoleSetByRoleSetCd);
    }
    
    //insert
    export function addRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.addRoleSet, command);
    }
    
    //update
    export function updateRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.updateRoleSet, command);
    }
    //delete
    export function removeRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.removeRoleSet, command);
    }    
    
    //get all web menu
    export function getAllWebMenu() : JQueryPromise<any>{
        return ajax(paths.getAllWebMenus);
    }

    //get company id of login user
    export function getCompanyIdOfLoginUser() : JQueryPromise<any>{
        return ajax(paths.getCompanyIdOfLoginUser);
    }
    
    //get Role By Id
    export function getRoleById(comman) : JQueryPromise<any>{
        return ajax(paths.getRoleById, comman);
    }
}
