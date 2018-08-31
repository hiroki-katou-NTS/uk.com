module nts.uk.com.view.cmm018.shr {
    export module servicebase {
        var paths: any = {
            updateHistory: "workflow/approvermanagement/workroot/updateHistory",
            getAllDataCom: "workflow/approvermanagement/workroot/getbycom",
            getAllDataPr: "workflow/approvermanagement/workroot/getbyprivate",
            getNameAppType: "workflow/approvermanagement/workroot/find/applicationType",
            updateRoot: "workflow/approvermanagement/workroot/updateRoot",
            getInfoEmployee: "workflow/approvermanagement/workroot/getInforPerson",
            getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
            getNameConfirmType: "workflow/approvermanagement/workroot/find/confirmRootType",
            getWpInfo: "workflow/approvermanagement/workroot/find/wpInfo",
            getWpLogin: "workflow/approvermanagement/workroot/find-wpInfo-login"
        }
        
        export function updateHistory(data): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.updateHistory, data);
        }
        export function getAllDataCom(param): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getAllDataCom, param);
        }
        export function getAllDataPr(param): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getAllDataPr, param);
        }
        export function getNameAppType(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getNameAppType);
        }
        export function updateRoot(data): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.updateRoot, data);
        }
        export function getInfoEmployee(employeeId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getInfoEmployee, employeeId);
        }
        export function getInfoEmLogin(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getInfoEmLogin);
        }
        export function getNameConfirmType(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getNameConfirmType);
        }
        export function getWpInfo(workplaceId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getWpInfo, workplaceId);
        }
        //get wpName
        export function getWpName(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getWpLogin);
        }
    } 
}