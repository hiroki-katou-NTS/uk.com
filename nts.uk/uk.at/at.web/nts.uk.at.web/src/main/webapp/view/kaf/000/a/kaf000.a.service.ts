 module kaf000.a.service{
    var paths = {
        getDataApprovalRoot : "at/request/application/getdataapprovalroot",
        getMessageDeadline : "at/request/application/getmessagedeadline"
    }
     /**
      * get all Data Approval Root 
      */
     export function getDataApprovalRoot(objApprovalRootInput ) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.getDataApprovalRoot,objApprovalRootInput);
     }
     getMessageDeadline
     /**
      * get getMessageDeadline
      */
     export function getMessageDeadline(inputMessageDeadline ) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getMessageDeadline,inputMessageDeadline);
     }
 }