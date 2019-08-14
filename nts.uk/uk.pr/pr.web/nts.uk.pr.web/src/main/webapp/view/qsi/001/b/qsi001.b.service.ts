module nts.uk.pr.view.qsi001.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getInforOnWelfPenInsurAccById: "shared/employeesociainsur/getInforOnWelfPenInsurAccById/{0}",
            getPersonInfo: "shared/employeesociainsur/getPersonInfo/{0}",
            getCorEmpWorkHisByEmpId: "shared/employeesociainsur/getCorEmpWorkHisByEmpId/{0}"
        };

        export function getInforOnWelfPenInsurAccById(empId : string){
            let _path = nts.uk.text.format(path.getInforOnWelfPenInsurAccById, empId);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getPersonInfo(empId : string){
            let _path = nts.uk.text.format(path.getPersonInfo, empId);
            return nts.uk.request.ajax("pr", _path);
        }

        export function getAllCorEmpWorkHisByEmpId(empId: string){
            let _path = nts.uk.text.format(path.getAllCorEmpWorkHisByEmpId, empId);
            return nts.uk.request.ajax("pr", _path);
        }

    }
}