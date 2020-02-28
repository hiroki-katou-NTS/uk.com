module nts.uk.at.view.ksu001.jb.service {
    var paths: any = {
        registerWorkPairPattern: "at/schedule/shift/team/workpairpattern/register",
        deleteWorkPairPattern: "at/schedule/shift/team/workpairpattern/delete",
        getDataComPattern: "screen/at/schedule/basicschedule/getDataComPattern",
        getDataWkpPattern: "screen/at/schedule/basicschedule/getDataWkpPattern"
    }

    export function registerWorkPairPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerWorkPairPattern, obj);
    }

    export function deleteWorkPairPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.deleteWorkPairPattern, obj);
    }

    export function getDataComPattern(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataComPattern);
    }

    export function getDataWkpPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWkpPattern, obj);
    }
}