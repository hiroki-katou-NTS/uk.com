module nts.uk.com.view.jhn002.b.service {
    var paths: any = {
        start: "hr/notice/report/regis/report/agent/getListDepartment",
        getEmployeeByDepartmentId: "hr/notice/report/regis/report/agent/getEmployeeByDepartmentId",
        searchEmployeeBykey: "hr/notice/report/regis/report/agent/searchEmployeeBykey"
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