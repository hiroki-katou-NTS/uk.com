 module ksu001.a.service {
    var paths: any = {
        getDataBasicSchedule: "screen/at/schedule/basicschedule/getData",
        registerData: "at/schedule/basicschedule/register"
    }

    export function getDataBasicSchedule(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataBasicSchedule, obj);
    }
     
     export function registerData(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerData, obj);
    }
}