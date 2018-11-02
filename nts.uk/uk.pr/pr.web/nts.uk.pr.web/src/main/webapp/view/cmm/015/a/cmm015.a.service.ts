module nts.uk.pr.view.cmm015.a.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getAllSalaryClassificationInfo: "core/wageprovision/organizationinformation/salaryclassification/salaryclassificationmaster/getAll",
        addSalaryClassificationInfo: "core/wageprovision/organizationinformation/salaryclassification/salaryclassificationmaster/add",
        removeSalaryClassificationInfo: "core/wageprovision/organizationinformation/salaryclassification/salaryclassificationmaster/remove",
        updateSalaryClassificationInfo: "core/wageprovision/organizationinformation/salaryclassification/salaryclassificationmaster/update",
    };

    export function getAllSalaryClassificationInformation(): JQueryPromise<Array<viewmodel.model.SalaryClassificationInformation>> {
        return ajax("pr", paths.getAllSalaryClassificationInfo);
    }

    export function addSalaryClassificationInformation(data: any) {
        return ajax("pr", paths.addSalaryClassificationInfo, data);
    }

    export function removeSalaryClassificationInformation(code: string) {
        return ajax("pr", paths.removeSalaryClassificationInfo, code);
    }

    export function updateSalaryClassificationInformation(data: any) {
        return ajax("pr", paths.updateSalaryClassificationInfo, data);
    }
}