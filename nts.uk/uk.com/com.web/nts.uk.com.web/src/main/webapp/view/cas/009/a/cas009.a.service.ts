module cas009.a.service {
    var paths: any = {
        getPersonInfoRole: "ctx/sys/auth/role/find/person/role",
        deletePersonInfoRole: "ctx/sys/auth/role/remove/person/infor",
        savePersonInfoRole: "ctx/sys/auth/role/save/person/infor",
        getOptItemEnum: 'ctx/sys/auth/role/get/enum/reference/range',
        userHasRole: 'ctx/sys/auth/role/user/has/role'

    }

    /** Get Role */
    export function getPersonInfoRole(roleIds: Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getPersonInfoRole, roleIds);
    }

    /** Function is used to delete Role */
    export function deleteRole(query: any): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.deletePersonInfoRole, query);
    }

    /** Update Role */
    export function saveRole(role: any): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.savePersonInfoRole, role);
    }
    /**
     * Call service to get optional item enum
     */
    export function getOptItemEnum(): JQueryPromise<model.EnumConstantDto> {
        return nts.uk.request.ajax(paths.getOptItemEnum);
    }

    export function userHasRole(): JQueryPromise<boolean> {
        return nts.uk.request.ajax("com", paths.userHasRole + "/8");
    }

    export module model {
        export interface EnumConstantDto {
            value: number;
            fieldName: string;
            localizedName: string;
        }
    }

}