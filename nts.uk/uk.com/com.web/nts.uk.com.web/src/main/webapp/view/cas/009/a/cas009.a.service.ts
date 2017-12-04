module cas009.a.service {
    var paths: any = {
        getPersonInfoRole: "ctx/sys/auth/role/find/person/role",
        deletePersonInfoRole: "ctx/sys/auth/role/delete/person/infor",
        savePersonInfoRole: "ctx/sys/auth/role/save/person/infor",
        getOptItemEnum: 'ctx/sys/auth/role/get/enum/reference/range'
    }
    
    /** Get Role */
    export function getPersonInfoRole(roleIds: Array<string>): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax("com", paths.getPersonInfoRole);
    }

    /** Function is used to delete Role */
    export function deleteRole(query: any): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.deletePersonInfoRole, query);
    }
    
    /** Update Role */
    export function saveRole(role: viewmodel.model.Role): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.savePersonInfoRole, role );
    }
    /**
     * Call service to get optional item enum
     */
    export function getOptItemEnum(): JQueryPromise<model.EnumConstantDto> {
        return nts.uk.request.ajax(paths.getOptItemEnum);
    }
    
    export module model {
              export interface EnumConstantDto {
                value: number;
                fieldName: string;
                localizedName: string;
     }
    }

}