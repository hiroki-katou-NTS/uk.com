module cmm051.a.service {
    var paths: any = {
        findWorkplace: "workplace/manager/find",
        findAllWkpManager: "at/auth/workplace/manager/findAll",
        addWorkplace: "workplace/manager/add/",
        updateWorkplace: "workplace/manager/update/",
        deleteWorkplace: "workplace/manager/delete/",
//        searchEmployeeByLogin: "basic/organization/employee/onlyemployee"
    }

//    export function findWorkplace(parameter: any): JQueryPromise<viewmodel.model.AgentDto> {
//        return nts.uk.request.ajax("at", paths.findAgent, parameter);
//    }

    export function findAllWkpManager(): JQueryPromise<Array<base.IWorkplaceManager>> {
        return nts.uk.request.ajax("at", paths.findAllWkpManager);
    }

    export function addWorkplace(workplace: any) {
        return nts.uk.request.ajax("at", paths.addWorkplace, workplace);
    }

    export function updateWorkplace(workplace: any) {
        return nts.uk.request.ajax("at", paths.updateWorkplace, workplace);
    }
    
    export function deleteWorkplace(workplace: any) {
        return nts.uk.request.ajax("at", paths.deleteWorkplace, workplace);
    }

//    export function searchEmployeeByLogin(baseDate: Date): JQueryPromise<any> {
//        return nts.uk.request.ajax('com', paths.searchEmployeeByLogin, baseDate);
//    }
}