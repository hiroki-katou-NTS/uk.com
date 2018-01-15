module nts.uk.at.view.kml002.h.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findByCid: "ctx/at/schedule/budget/fixedverticalsetting/findByCid",
        addFixedVertical: "ctx/at/schedule/budget/fixedverticalsetting/addFixedVertical",
        updateFixedVertical: "ctx/at/schedule/budget/fixedverticalsetting/updateFixedVertical"
    }
    
    export function findByCid(): JQueryPromise<Array<JQueryPromise<Array<viewmodel.ITotalTime>> >> {
        return nts.uk.request.ajax("at",paths.findByCid);
    }
    export function addFixedVertical(totaltimes: any) {
        return nts.uk.request.ajax("at", paths.addFixedVertical, totaltimes);
    }

    export function updateFixedVertical(totaltimes: any) {
        return nts.uk.request.ajax("at",paths.updateFixedVertical, totaltimes);
    }
}
