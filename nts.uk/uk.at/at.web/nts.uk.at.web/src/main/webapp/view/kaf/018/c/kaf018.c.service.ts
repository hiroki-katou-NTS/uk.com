module nts.uk.at.view.kaf018.c.service {
    var paths: any = {
        getDataSpecDateAndHoliday: "screen/at/schedule/basicschedule/getDataSpecDateAndHoliday",
    }
    export function getDataSpecDateAndHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataSpecDateAndHoliday, obj);
    }
}