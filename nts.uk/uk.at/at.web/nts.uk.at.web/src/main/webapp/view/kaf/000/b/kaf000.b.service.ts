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
        deleteApp : "at/request/application/deleteapp",
        getAppDataDate : "at/request/application/getAppDataByDate",
        getAppByID: "at/request/application/getAppInfoByAppID"
    }
     
     export function getAppDataDate(command) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getAppDataDate,command);
     } 
     
     /**
      * approve application
      */
     export function approveApp(command) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.approveApp,command);
     }  
     
     /**
      * deny  application
      */
     export function denyApp(command) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.denyApp,command);
     }
     
      /**
      * release   application
      */
     export function releaseApp(command) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.releaseApp,command);
     }
     
      /**
      * cancel application
      */
     export function cancelApp(command) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.cancelApp,command);
     }
     
     /**
      * delete application
      */
     export function deleteApp(command) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.deleteApp,command);
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
     export function getMessageDeadline(applicationMeta: any) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getMessageDeadline, applicationMeta);
     }
     
     export function getAppByID(appID: string) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getAppByID, appID);
     }
 }