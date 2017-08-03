module nts.uk.com.view.cas001.a.service {
    import ajax = nts.uk.request.ajax;
    var paths = {
        getPersonRoleList: "/ctx/bs/person/roles/findAll",
        getPersonRoleAuth: "/ctx/bs/person/roles/auth/find/{0}",
        getAllPersonCategoryAuthByRoleId: "ctx/bs/person/roles/auth/category/find/{0}"
    }
    export function getPersonRoleList(): JQueryPromise<any> {
        return ajax(paths.getPersonRoleList);
    }
    export function getPersonRoleAuth(roleID): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getPersonRoleAuth, roleID));
    }
    export function getAllPersonCategoryAuthByRoleId(roleID): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getAllPersonCategoryAuthByRoleId, roleID));
    }



}
