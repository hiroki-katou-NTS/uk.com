module nts.uk.at.view.kdw001.e.service {
    var paths: any = {
       getImplementationResult :"at/record/log/findByEmpCalAndSumExecLogID"
    }
    export function getImplementationResult(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getImplementationResult);
    }    
   
}