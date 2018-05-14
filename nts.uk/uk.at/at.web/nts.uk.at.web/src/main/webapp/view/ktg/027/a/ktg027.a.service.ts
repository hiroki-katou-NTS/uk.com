module nts.uk.at.view.ktg027.a.service {
    import ajax = nts.uk.request.ajax;
   
    
    var paths: any = {
         getListClosure: "screen/at/overtimehours/getlistclosure",
        getOvertimeHours: "screen/at/overtimehours/getovertimehours/",
        getOvertimeHour : "screen/at/overtimehours/buttonPressingProcess"
    }
    /** Get ListClosure */
    export function getListClosure(): JQueryPromise<any> {
        return ajax("at", paths.getListClosure);
    }
    /** Get ListClosure */
    export function getOvertimeHours(targetMonth : number): JQueryPromise<any> {
        return ajax("at", paths.getOvertimeHours + targetMonth);
    }
    
    /** Get ListClosure */
    export function buttonPressingProcess(targetMonth : number, closureID :number): JQueryPromise<any> {
        var data = {
            targetMonth : targetMonth,
            closureID : closureID };
        
        return ajax("at", paths.getOvertimeHours ,data);
    }
}  