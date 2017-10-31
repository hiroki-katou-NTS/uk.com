 module nts.uk.at.view.kaf000.a.service{
    var paths = {
        getDataApprovalRoot : "at/request/application/getdataapprovalroot",
        getMessageDeadline : "at/request/application/getmessagedeadline"
    }
     /**
      * get all Data Approval Root 
      */
     export function getDataApprovalRoot(objApprovalRootInput: any ) : JQueryPromise<Array<any>>{
         return nts.uk.request.ajax("at",paths.getDataApprovalRoot,objApprovalRootInput);
     }
     
     /**
      * get getMessageDeadline
      */
     export function getMessageDeadline(applicationMeta: any ) : JQueryPromise<any>{
         return nts.uk.request.ajax("at",paths.getMessageDeadline, applicationMeta);
     }
 }