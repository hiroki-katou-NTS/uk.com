module jcm007.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        'getData': 'databeforereflecting/getData',
        'findEmployeeInfo': 'employee-info/find',
        'checkStatusRegistration': 'databeforereflecting/checkStatusRegistration/{0}',
        'preCheck': 'databeforereflecting/register/preCheck',
        'add': 'databeforereflecting/add',
        'update': 'databeforereflecting/update',
        'remove': 'databeforereflecting/remove/{0}' 
    }
       
    export function getData() : JQueryPromise<any>{
        return ajax(paths.getData);
    }
    
    export function findEmployeeInfo(data): JQueryPromise<any> {
        return ajax(paths.findEmployeeInfo, data);
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
    
    export function updateRetireeInformation(command: any) : JQueryPromise<any>{
        return ajax(paths.update, command);
    }
    
    export function remove(hisId: any) : JQueryPromise<any>{
        return ajax('hr', format(paths.remove, hisId ));
    }

   
}