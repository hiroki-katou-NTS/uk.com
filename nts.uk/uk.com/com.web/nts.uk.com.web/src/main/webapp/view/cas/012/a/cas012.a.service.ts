module nts.uk.com.view.cas012.a.service {
    var paths: any = {
        getMetadata: "ctx/sys/auth/grant/roleindividual/getmetadata",
        getAll: "ctx/sys/auth/grant/roleindividual/findall",
        createRoleIndividual: "ctx/sys/auth/grant/roleindividual/create",
        updateRoleIndividual: "ctx/sys/auth/grant/roleindividual/update",
        deleteRoleIndividual: "ctx/sys/auth/grant/roleindividual/delete"
    }

    export function getMetadata(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getMetadata);
    }
    
    export function getAll(selectedRoleType: number, selectedCompany: string): JQueryPromise<any> {
        var data = {
            selectedCompany: selectedCompany,
            selectedRoleType: selectedRoleType
        };
        return nts.uk.request.ajax("com", paths.getAll, data);
    }

    export function create(roleIndividual: viewmodel.RoleIndividualGrantBaseCommand): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.createRoleIndividual, roleIndividual);
    }
    
    export function update(roleIndividual: viewmodel.RoleIndividualGrantBaseCommand): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.updateRoleIndividual, roleIndividual);
    }

    export function deleteRoleIndividual(userID: string, companyID: string, roleType: number): JQueryPromise<any> {
        var data = {
            userID: userID,
            companyID: companyID,
            selectedRoleType: roleType
        };
        return nts.uk.request.ajax("com", paths.deleteRoleIndividual, data);
    }

}