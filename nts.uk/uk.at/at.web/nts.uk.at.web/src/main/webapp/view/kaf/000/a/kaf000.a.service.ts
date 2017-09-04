 module kaf000.a.service{
    var paths = {
        getAllPhaseByAppID: "at/request/appapprovalphase/getallphase",
        getAllFrameByPhaseID: "at/request/approvalframe/getallframe"
    }
    export function getAllPhaseByAppID(appID) : JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at",paths.getAllPhaseByAppID+"/"+appID);
    }
    export function getAllFrameByPhaseID(phaseID) : JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at",paths.getAllFrameByPhaseID+"/"+phaseID);
    }
     
 }