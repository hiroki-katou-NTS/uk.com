module nts.uk.at.view.kaf018.f.service {
    var paths: any = {
        getDataSpecDateAndHoliday: "screen/at/schedule/basicschedule/getDataSpecDateAndHoliday",
        getEmpPerformance: "at/record/application/realitystatus/getEmpPerformance"
    }
    
    export function getDataSpecDateAndHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataSpecDateAndHoliday, obj);
    }
    
    export function getEmpPerformance(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getEmpPerformance, obj);
    }
}