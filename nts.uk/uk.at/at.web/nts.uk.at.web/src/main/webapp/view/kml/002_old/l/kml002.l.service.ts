module kml002.l.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findAll: "at/shared/scherec/totaltimes/getallitem",
        addTotalTimes: "ctx/at/schedule/budget/fixedverticalsetting/addVerticalCnt",
        findAllCountNo:  "ctx/at/schedule/budget/fixedverticalsetting/findCnt"
    }
    
    export function findAll(): JQueryPromise<Array<viewmodel.ItemModel>> {
        return nts.uk.request.ajax("at",paths.findAll);
    }
    export function addTotalTimes(totaltimes: any) {
        return nts.uk.request.ajax("at", paths.addTotalTimes, totaltimes);
    }
    export function findAllCountNo(fixedItemAtr) {
        return nts.uk.request.ajax("at",paths.findAllCountNo + "/" +fixedItemAtr);
    }
}