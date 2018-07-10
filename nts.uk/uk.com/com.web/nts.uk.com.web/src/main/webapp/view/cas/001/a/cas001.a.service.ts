module nts.uk.com.view.cas001.a.service {
    import ajax = nts.uk.request.ajax;
    import block = nts.uk.ui.block;
    import format = nts.uk.text.format;
    var paths = {
        getCategoryRoleList: "/ctx/pereg/roles/auth/category/findAllCategory/{0}",
        getCategoryAuth: "/ctx/pereg/roles/auth/category/find/{0}/{1}",
        getPersonRoleItemList: "/ctx/pereg/roles/auth/item/findAllItem/{0}/{1}",
        savePersonRole: "/ctx/pereg/roles/auth/save",
        getAllItemIdRequired: "ctx/pereg/person/info/ctgItem/layout/findAll/required"
    }
    
    export function getCategoryRoleList(roleID): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(format(paths.getCategoryRoleList, roleID))
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();

    }

    export function getCategoryAuth(roleId, personInfoCategoryAuthId): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(format(paths.getCategoryAuth, roleId, personInfoCategoryAuthId))
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getPersonRoleItemList(roleId, personInfoCategoryAuthId): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(format(paths.getPersonRoleItemList, roleId, personInfoCategoryAuthId))
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function savePersonRole(command): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(paths.savePersonRole, command)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getAllItemIdRequired(): JQueryPromise<Array<any>> {
       return nts.uk.request.ajax(paths.getAllItemIdRequired);
    }

}
