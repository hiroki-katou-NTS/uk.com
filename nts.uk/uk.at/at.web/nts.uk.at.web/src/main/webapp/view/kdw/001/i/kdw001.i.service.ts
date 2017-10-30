module nts.uk.at.view.kdw001.i {
    export module service {
        var paths = {
            getByEmpCalAndSumExeLogId : "at/record/log/getallbyempid"
        }
        
        /**
         * get all EmpCalAndSumExeLog by startDate and endDate
         */
        export function getByEmpCalAndSumExeLogId(empCalAndSumExeLogId: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getByEmpCalAndSumExeLogId+"/"+empCalAndSumExeLogId);
        }
    
    }
}
