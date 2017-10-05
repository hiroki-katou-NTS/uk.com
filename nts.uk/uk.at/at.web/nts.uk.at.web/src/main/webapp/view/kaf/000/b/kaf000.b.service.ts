 module nts.uk.at.view.kaf000.b.service{
    var paths = {
        getAllDataByAppID: "at/request/application/getalldatabyappid",
        getAllReasonByAppID : "at/request/application/getreasonforremand",
        getDetailCheck :  "at/request/application/getdetailcheck",
        getMessageDeadline : "at/request/application/getmessagedeadline",
        approveApp : "at/request/application/approveapp",
        denyApp : "at/request/application/denyapp",
        releaseApp : "at/request/application/releaseapp",
        cancelApp : "at/request/application/cancelapp",
        deleteApp : "at/request/application/deleteapp"
    }
     
     /**
      * approve application
      */
     export function approveApp(appID) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.approveApp+"/"+appID);
     }  
     
     /**
      * deny  application
      */
     export function denyApp(appID) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.denyApp+"/"+appID);
     }
     
      /**
      * release   application
      */
     export function releaseApp(appID) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.releaseApp+"/"+appID);
     }
     
      /**
      * cancel application
      */
     export function cancelApp(appID) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.cancelApp+"/"+appID);
     }
     
     /**
      * delete application
      */
     export function deleteApp(appID) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.deleteApp+"/"+appID);
     }
     
     
     /**
      * get all phase by applicationID
      */
     export function getAllDataByAppID(appID) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getAllDataByAppID+"/"+appID);
     }  
     /**
      * get all reason by applicationID
      */
     export function getAllReasonByAppID(appID) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.getAllReasonByAppID+"/"+appID);
     }  
     
     /**
      * get detail check
      */
     export function getDetailCheck(inputGetDetailCheck) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getDetailCheck,inputGetDetailCheck);
     }  
     
      /**
      * get getMessageDeadline
      */
     export function getMessageDeadline(inputMessageDeadline ) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getMessageDeadline,inputMessageDeadline);
     }
 }