module nts.uk.at.view.kdw001.i {
    export module service {
        var paths = {
            getByEmpCalAndSumExeLogId : "at/record/log/getallbyempid",
            getListTargetPersonByEmpId : "at/record/target/getbyempid",
            getAllErrMessageInfoByEmpID : "at/record/message/getallbyempid"
        }
        
        /**
         * get  EmpCalAndSumExeLog by empCalAndSumExeLogId
         */
        export function getByEmpCalAndSumExeLogId(empCalAndSumExeLogId: any ) : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getByEmpCalAndSumExeLogId+"/"+empCalAndSumExeLogId);
        }
        /**
         * get getListTargetPersonByEmpId by  empCalAndSumExeLogId
         */
         export function getListTargetPersonByEmpId(empCalAndSumExeLogId: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getListTargetPersonByEmpId+"/"+empCalAndSumExeLogId);
        }
        
        /**
         * get getAllErrMessageInfoByEmpID by  empCalAndSumExeLogId
         */
         export function getAllErrMessageInfoByEmpID(empCalAndSumExecLogID: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllErrMessageInfoByEmpID+"/"+empCalAndSumExecLogID);
        }
    
    }
}
