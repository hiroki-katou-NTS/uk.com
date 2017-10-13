module kml002.l.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findAll: "ctx/at/schedule/shift/totaltimes/getallitem",
        addTotalTimes: "ctx/at/schedule/shift/totaltimes/save",
        updateTotalTimes: "ctx/at/schedule/shift/totaltimes/save"
    }
    
    export function findAll(): JQueryPromise<Array<viewmodel.ItemModel>> {
        return nts.uk.request.ajax("at",paths.findAll);
    }
    export function addTotalTimes(totaltimes: any) {
        return nts.uk.request.ajax("at", paths.addTotalTimes, totaltimes);
    }

    export function updateTotalTimes(totaltimes: any) {
        return nts.uk.request.ajax("at",paths.updateTotalTimes, totaltimes);
    }
}