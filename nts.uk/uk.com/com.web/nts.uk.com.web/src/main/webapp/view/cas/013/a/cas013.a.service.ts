module nts.uk.com.view.cas013.a.service {
    var paths: any = {
        getAllRoleIndividualCom: "ctx/sys/auth/grant/roleindividualCom/findAll",
    }

    export function getAllRoleIndividualCom(selectedRoleType: number): JQueryPromise<any> {
        var data = {
            selectedRoleType: selectedRoleType
        };
        return nts.uk.request.ajax("com", paths.getAllRoleIndividualCom);
    }

}