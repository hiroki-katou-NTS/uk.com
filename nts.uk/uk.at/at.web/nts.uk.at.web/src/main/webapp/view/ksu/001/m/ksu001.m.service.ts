module nts.uk.at.view.ksu001.m.service {
    var paths: any = {
        startPage: "ctx/at/schedule/setting/employee/rank/startPage",
        regis : "ctx/at/schedule/setting/employee/rank/regis"
        

    }
    
    export function startPage(listEmpID: string[]): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startPage, listEmpID);
    }
    
    export function regis(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.regis, command);
    }
}