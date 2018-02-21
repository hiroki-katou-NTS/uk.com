module nts.uk.com.view.cmf001.f.service {
    import ajax = nts.uk.request.ajax;
    import block = nts.uk.ui.block;
    import format = nts.uk.text.format;
    var paths = {
        getPersonRoleAuth: "/ctx/pereg/roles/auth/find/{0}",
    }
    export function getPersonRoleAuth(roleID): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(format(paths.getPersonRoleAuth, roleID))
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

}