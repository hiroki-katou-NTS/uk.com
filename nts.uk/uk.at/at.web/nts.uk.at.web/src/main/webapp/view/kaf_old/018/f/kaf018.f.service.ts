module nts.uk.at.view.kaf018_old.f.service {
    var paths: any = {
        getDataSpecDateAndHoliday: "screen/at/schedule/basicschedule/getDataSpecDateAndHoliday",
        getEmpPerformance: "at/record/application/realitystatus/getEmpPerformance",
        getUseSetting: "at/record/application/realitystatus/getUseSetting"
    }

    export function getDataSpecDateAndHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataSpecDateAndHoliday, obj);
    }

    export function getEmpPerformance(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getEmpPerformance, obj);
    }
    export function getUseSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getUseSetting);
    }
}