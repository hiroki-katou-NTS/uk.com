module nts.uk.com.view.cmm018.shr {
    export module servicebase {
        var paths: any = {
            updateHistory: "workflow/approvermanagement/workroot/updateHistory",
            getAllDataCom: "workflow/approvermanagement/workroot/getbycom",
            getAllDataPr: "workflow/approvermanagement/workroot/getbyprivate",
            getNameAppType: "workflow/approvermanagement/workroot/find/applicationType"
        }
        
        export function updateHistory(data): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateHistory, data);
        }
        export function getAllDataCom(param): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAllDataCom, param);
        }
        export function getAllDataPr(param): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAllDataPr, param);
        }
        export function getNameAppType(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getNameAppType);
        }
    } 
}