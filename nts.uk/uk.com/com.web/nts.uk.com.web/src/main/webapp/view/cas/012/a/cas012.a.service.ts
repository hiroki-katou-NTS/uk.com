module nts.uk.com.view.cas012.a.service {
    var paths: any = {
        getAll: "ctx/sys/auth/grant/roleindividual/findall",
        create: "ctx/sys/auth/grant/roleindividual/create",
        deleteRoleIndividual: "ctx/sys/auth/grant/roleindividual/delete"
    }

    export function getAll(selectedCompany: string, selectedRoleType: number): JQueryPromise<any> {
        var data = {
            selectedCompany: selectedCompany,
            selectedRoleType: selectedRoleType
        };
        return nts.uk.request.ajax("com", paths.getAll, data);
    }

    export function create(roleIndividual: viewmodel.RoleIndividual): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.create, roleIndividual);
    }

    export function deleteRoleIndividual(userID: string, companyID: string, selectedRoleType: number): JQueryPromise<any> {
        var data = {
            userID: userID,
            companyID: companyID,
            selectedRoleType: selectedRoleType
        };
        return nts.uk.request.ajax("com", paths.deleteRoleIndividual, data);
    }

}