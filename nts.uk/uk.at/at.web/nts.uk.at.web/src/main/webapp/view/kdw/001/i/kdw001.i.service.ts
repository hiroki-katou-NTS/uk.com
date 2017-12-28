module nts.uk.at.view.kdw001.i {
    export module service {
        var paths = {
            getByEmpCalAndSumExeLogId : "at/record/log/getallbyempid",
            getListTargetPersonByEmpId : "at/record/target/getbyempid",
            getAllErrMessageInfoByEmpID : "at/record/message/getallbyempid",
            getListPersonInforLog : "at/record/personlog/getallbylistsid"
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
    
        /**
         * get all person infor by list sid 
         */
        export function getListPersonInforLog(listSid : Array<string>) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getListPersonInforLog,listSid);
        }
    }
}
