module nts.uk.at.view.ktg027.a.service {
    import ajax = nts.uk.request.ajax;
   
    
    var paths: any = {
         getListClosure: "screen/at/overtimehours/getlistclosure",
        getOvertimeHours: "screen/at/overtimehours/getovertimehours/",
        buttonPressingProcess : "screen/at/overtimehours/buttonPressingProcess/{0}/{1}"
    }
    /** Get ListClosure */
    export function getListClosure(): JQueryPromise<any> {
        return ajax("at", paths.getListClosure);
    }
    /** Start page */   
    export function getOvertimeHours(targetMonth : number): JQueryPromise<any> {
        return ajax("at", paths.getOvertimeHours + targetMonth);
    }
    
    /** Get ListClosure */
    export function buttonPressingProcess(targetMonth : number, selectedClosureID :number): JQueryPromise<any> {
       let _path = nts.uk.text.format(paths.buttonPressingProcess, targetMonth, selectedClosureID);
        return ajax("at", _path);
    }
    /** Print */
    export function saveAsCsv(data:any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "overtime", domainType: "OvertimeHours", languageId: 'ja', reportType: 3 ,data:data});
        }
}  