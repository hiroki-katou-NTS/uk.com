 module ksu001.o.service {
    var paths: any = {
        getWorkType: "screen/at/schedule/basicschedule/getListWorkType",
        getWorkTime: "screen/at/schedule/basicschedule/getListWorkTime"
    }

    export function getWorkType(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkType);
    }
     
     export function getWorkTime(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkTime);
    }
}