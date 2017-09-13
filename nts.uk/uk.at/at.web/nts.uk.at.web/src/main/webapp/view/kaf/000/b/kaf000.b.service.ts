 module kaf000.b.service{
    var paths = {
        getAllPhaseByAppID: "at/request/appapprovalphase/getallphase",
        getAllFrameByPhaseID: "at/request/approvalframe/getallframe",
        getAllFrameByListPhaseId : "at/request/approvalframe/getallframebylistphaseid",
        getAllFrameByListPhaseId1 : "at/request/approvalframe/getallframebylistphaseid1"
    }
     /**
      * get all phase by applicationID
      */
     export function getAllPhaseByAppID(appID) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.getAllPhaseByAppID+"/"+appID);
     }  
     /**
      * get all frame by list phase ID 1
      */
     export function getAllFrameByListPhaseId1(listPhaseId) : JQueryPromise<Array<Array<any>>>{
         return nts.uk.request.ajax("at",paths.getAllFrameByListPhaseId1,listPhaseId);
     }
 }