module nts.uk.at.view.kdl006.a.service {
    var paths = {
        getInfo : "screen/at/kdl014/get",
        findStampMeanSmartPhone : "screen/at/kdl014/find/stampMeans/smart-phone",
        findstampMeanTimeClock : "screen/at/kdl014/find/stampMeans/time-clock"
    }
        
    export function getInfo(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getInfo, param);
    }
    
       export function findStampMeanSmartPhone(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findStampMeanSmartPhone);
    }
    
       export function findstampMeanTimeClock(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findstampMeanTimeClock);
    }
}

