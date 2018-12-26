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

    export function deleteRoleIndividual(roleIndividual: viewmodel.RoleIndividualGrantBaseCommand): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.deleteRoleIndividual, roleIndividual);
    }

    export function exportExcel(date: string): JQueryPromise<any> {
        let _params = {domainId: "GrantAdminRole",
            domainType: "CAS012管理者ロールの付与",
            languageId: 'ja',
            reportType: 0,
            baseDate: date
        };
        return nts.uk.request.exportFile('/masterlist/report/print', _params);
    }

}