module jcm007.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        'getData': 'databeforereflecting/getData',
        'checkStatusRegistration': 'databeforereflecting/checkStatusRegistration/{0}',
        'preCheck': 'databeforereflecting/preCheck',
        'addRetireeInformation': 'databeforereflecting/addRetireeInformation',  
    }
       
    export function getData() {
        return ajax(paths.getData);
    }
    
    export function CheckStatusRegistration(sid: any) {
        return ajax('hr', format(paths.checkStatusRegistration, sid ));
    }

    export function preCheck(command: any) {
        return ajax(format(paths.preCheck, command));
    }

    export function addRetireeInformation(command: any) {
        return ajax(format(paths.addRetireeInformation, command));
    }

   
}