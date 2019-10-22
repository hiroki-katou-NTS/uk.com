module jcm007.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        'getData': 'databeforereflecting/getData',
        'getEmployeeInfo' : 'query/ccg029employee/getEmpInfo',
        'checkStatusRegistration': 'databeforereflecting/checkStatusRegistration/{0}',
        'preCheck': 'databeforereflecting/register/preCheck',
        'add': 'databeforereflecting/add',  
    }
       
    export function getData() : JQueryPromise<any>{
        return ajax(paths.getData);
    }
    
    export function getEmployeeInfo(query : any) : JQueryPromise<any>{
        return ajax(paths.getEmployeeInfo , query);
    }
    
    export function CheckStatusRegistration(sid: any) : JQueryPromise<any> {
        return ajax('hr', format(paths.checkStatusRegistration, sid ));
    }

    export function preCheck(command: any) : JQueryPromise<any> {
        return ajax(paths.preCheck, command);
    }

    export function addRetireeInformation(command: any) : JQueryPromise<any>{
        return ajax(paths.add, command);
    }

   
}