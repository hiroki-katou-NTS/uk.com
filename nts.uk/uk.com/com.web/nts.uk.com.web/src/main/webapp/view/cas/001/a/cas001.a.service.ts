module nts.uk.com.view.cas001.a.service {
    import ajax = nts.uk.request.ajax;
    var paths = {
        getPersonRoleList: "/ctx/bs/person/roles/findAll",
        getPersonRoleAuth: "/ctx/bs/person/roles/auth/find/{0}",
        getCategoryRoleList: "/ctx/bs/person/roles/auth/category/findAllCategory/{0}",
        getAuthDetailByPId: "/ctx/bs/person/roles/auth/category/findDetail/{0}",
        getPersonRoleItemList: "ctx/bs/person/roles/auth/item/findAllItem/{0}",
    }

    export function getPersonRoleList(): JQueryPromise<any> {
        return ajax(paths.getPersonRoleList);
    }

    export function getPersonRoleAuth(roleID): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getPersonRoleAuth, roleID));
    }

    export function getCategoryRoleList(roleID): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getCategoryRoleList, roleID));
    }

    export function getAuthDetailByPId(personInfoCategoryAuthId): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getAuthDetailByPId, personInfoCategoryAuthId));
    }

    export function getPersonRoleItemList(personInfoCategoryAuthId): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getPersonRoleItemList, personInfoCategoryAuthId));
    }




}
