module nts.uk.com.view.cas011.a.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
            getCompanyIdOfLoginUser:    "ctx/sys/auth/roleset/companyidofloginuser",
            getAllRoleSet:              "ctx/sys/auth/roleset/findallroleset",
            getRoleSetByRoleSetCd:      "ctx/sys/auth/roleset/findroleset/{0}",
            addRoleSet:                 "ctx/sys/auth/roleset/addroleset",
            updateRoleSet:              "ctx/sys/auth/roleset/updateroleset",
            removeRoleSet:              "ctx/sys/auth/roleset/deleteroleset",            
            getAllWebMenu:              "ctx/sys/auth/roleset/findallwebmenu",
            getRoleById:                "ctx/sys/auth/role/getrolebyroleid/{roleid}"
    }

    //get all role set
    export function getAllRoleSet() : JQueryPromise<any>{
        return ajax(paths.getAllRoleSet);
    }

    //get role set
    export function getRoleSetByRoleSetCd(roleSetCd) : JQueryPromise<any>{
        return ajax(nts.uk.text.format(paths.getRoleSetByRoleSetCd, roleSetCd));
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
        return ajax(paths.getAllWebMenu);
    }

    //get company id of login user
    export function getCompanyIdOfLoginUser() : JQueryPromise<any>{
        return ajax(paths.getCompanyIdOfLoginUser);
    }
    
    //get Role By Id
    export function getRoleById(command) : JQueryPromise<any>{
        return ajax(paths.getRoleById, command);
    }
}
