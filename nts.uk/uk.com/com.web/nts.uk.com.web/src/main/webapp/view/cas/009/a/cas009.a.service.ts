module cas009.a {
    import ajax = nts.uk.request.ajax;

    export const fetch = {
        get_permision: (roleId: string) => ajax('com', 'ctx/pereg/functions/auth/find-all', roleId),
        save_permision: (command: any) => ajax('com', 'ctx/pereg/functions/auth/register', command)
    };

    export module service {

        var paths: any = {
            getPersonInfoRole: "ctx/sys/auth/role/find/person/role",
            deletePersonInfoRole: "ctx/sys/auth/role/remove/person/infor",
            savePersonInfoRole: "ctx/sys/auth/role/save/person/infor",
            getOptItemEnum: 'ctx/sys/auth/role/get/enum/reference/range',
            userHasRole: 'ctx/sys/auth/role/user/has/role'

        }

        /** Get Role */
        export function getPersonInfoRole(roleIds: Array<string>) {
            return ajax("com", paths.getPersonInfoRole, roleIds);
        }

        /** Function is used to delete Role */
        export function deleteRole(query: any) {
            return ajax("com", paths.deletePersonInfoRole, query);
        }

        /** Update Role */
        export function saveRole(role: any) {
            return ajax("com", paths.savePersonInfoRole, role);
        }
        /**
         * Call service to get optional item enum
         */
        export function getOptItemEnum() {
            return ajax(paths.getOptItemEnum);
        }

        export function userHasRole() {
            return ajax("com", paths.userHasRole + "/8");
        }
    }
}