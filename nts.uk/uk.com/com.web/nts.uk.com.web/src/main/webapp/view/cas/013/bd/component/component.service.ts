module nts.uk.com.view.cas13.b.component {
    export module service {
        var paths = {
            getListRoleByRoleType: "ctx/sys/auth/role/getlistrolebytype",
            getListUser: "ctx/sys/auth/user/getlistUser"
        }
        /**
         * get getListRoleByRoleType by roleType
         */
        export function getListUser(): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("com", paths.getListUser);
        }
        
    }
}
