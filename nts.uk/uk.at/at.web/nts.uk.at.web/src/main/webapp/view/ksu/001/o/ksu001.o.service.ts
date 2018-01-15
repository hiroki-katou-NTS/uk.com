module nts.uk.at.view.ksu001.o.service {
    var paths: any = {
        getWorkTypeTimeAndStartEndDate: "screen/at/schedule/basicschedule"
    }

    export function getWorkTypeTimeAndStartEndDate(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkTypeTimeAndStartEndDate);
    }
}