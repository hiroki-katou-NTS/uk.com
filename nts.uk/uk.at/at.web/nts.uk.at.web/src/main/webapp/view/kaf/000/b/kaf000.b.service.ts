 module kaf000.b.service{
    var paths = {
        getAllPhaseByAppID: "at/request/appapprovalphase/getallphase",
        getAllFrameByPhaseID: "at/request/approvalframe/getallframe",
        getAllReasonByAppID : "at/request/application/getreasonforremand"
    }
     /**
      * get all phase by applicationID
      */
     export function getAllPhaseByAppID(appID) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.getAllPhaseByAppID+"/"+appID);
     }  
     /**
      * get all reason by applicationID
      */
     export function getAllReasonByAppID(appID) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.getAllReasonByAppID+"/"+appID);
     }  
     
 }