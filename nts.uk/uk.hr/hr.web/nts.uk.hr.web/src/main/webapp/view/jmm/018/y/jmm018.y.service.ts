module nts.uk.com.view.jmm018.y.service {
    var paths: any = {
        start: "menuApprovalSettings/startDepartment",
        getEmployeeByDepartmentId: "menuApprovalSettings/getEmployeeByDepartmentId",
        searchEmployeeBykey: "menuApprovalSettings/searchEmployeeBykey"
    }
    
    export function start(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.start);
    }
    
    export function getEmployeeByDepartmentId(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getEmployeeByDepartmentId, param);
    }
    
    export function searchEmployeeBykey(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.searchEmployeeBykey, param);
    }
}