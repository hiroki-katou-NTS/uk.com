 module kaf000.b.service{
    var paths = {
        getAllDataByAppID: "at/request/application/getalldatabyappid",
        getAllReasonByAppID : "at/request/application/getreasonforremand",
        getDetailCheck :  "at/request/application/getdetailcheck"
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
 }